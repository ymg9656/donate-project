package com.daun.donate.main.web;

import com.daun.donate.schedule.ScheduledTasks;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    private static String CAMPAIGN_CONTRACT="0xbDb89BB1bBc9b2D0cf207FDEcb104DBe6998F860";
    private static BigInteger GASPRICE=BigInteger.valueOf(Long.parseLong("20000000000"));
    private static BigInteger GASLIMIT=BigInteger.valueOf(Long.parseLong("531717"));
    private static String PUBLIC_KEY="0x449962EceECE14cDa0EA7FaC770AAE5991a8048B";
    private static String PRIVATE_KEY="6a42628b93c43df10b7efd1c2389ebc234e7b7ba1699cc5a908cd1bebf04686c";



    @Autowired
    private Admin web3j;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public @ResponseBody String test(@RequestParam(required = true)Integer campaignIdx){
        BigInteger nonce=null;
        Web3ClientVersion web3ClientVersion = null;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(PUBLIC_KEY, DefaultBlockParameterName.LATEST).send();
            nonce = ethGetTransactionCount.getTransactionCount();
            nonce.add(BigInteger.valueOf(1));



            final Function function = new Function(
                    "getCampaignFailList",
                    Collections.<Type>emptyList(),
                    Arrays.asList(new TypeReference<DynamicArray<Uint8>>() {}));
            //Arrays.asList(new TypeReference<Type>() {}

            String encodedFunction = FunctionEncoder.encode(function);

            // Create new Transaction
            Transaction transaction=Transaction.createEthCallTransaction(PUBLIC_KEY,CAMPAIGN_CONTRACT,encodedFunction);
            org.web3j.protocol.core.methods.response.EthCall response=web3j.ethCall(transaction,DefaultBlockParameterName.LATEST).send();
            log.info("getCampaignList1 : "+response);
            if(response!=null){
                ObjectMapper mapper = new ObjectMapper();
                String ethSendTransactionLog=mapper.writeValueAsString(response);
                log.info("getCampaignList2 : "+ethSendTransactionLog);
            }


            List<Type> decode1 = FunctionReturnDecoder.decode(response.getValue(),function.getOutputParameters());

            try {

                List<Uint8> tArray = (List<Uint8>) decode1.get(0).getValue();
                for(Uint8 d:tArray){
                    System.out.println("getValue1 tArray = " + d.getValue());


                }

            }catch (Exception e){
                e.printStackTrace();
            }



            System.out.println("getValue1 = " + decode1.get(0).getValue());
            System.out.println("getType1 = " + decode1.get(0).getTypeAsString());

            List<Type> decode = FunctionReturnDecoder.decode(response.getResult(),function.getOutputParameters());

            System.out.println("getValue = " + decode.get(0).getValue());
            System.out.println("getType = " + decode.get(0).getTypeAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "rr";
    }


}
