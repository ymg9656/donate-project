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
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
import java.util.Collections;
import java.util.Date;

@Controller
public class MainController {

    private static String CAMPAIGN_CONTRACT="0xC13a358fdbDBBf5DAB52195C83aE025a1Cd273DD";
    private static BigInteger GASPRICE=BigInteger.valueOf(Long.parseLong("20000000000"));
    private static BigInteger GASLIMIT=BigInteger.valueOf(Long.parseLong("531717"));
    private static String PUBLIC_KEY="0x06c1604d4277c6E845f375EC1C6F6c1feAAf4890";
    private static String PRIVATE_KEY="fafa80853dd96a9382368940a4b899aa63ef221e741597ff20d78c2b87649361";



    @Autowired
    private Admin web3j;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/deleteCampaign",method = RequestMethod.GET)
    public String deleteCampaign(@RequestParam(required = true)Integer campaignIdx){
        BigInteger nonce=null;
        Web3ClientVersion web3ClientVersion = null;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(PUBLIC_KEY, DefaultBlockParameterName.LATEST).send();
            nonce = ethGetTransactionCount.getTransactionCount();
            nonce.add(BigInteger.valueOf(1));

            Bytes32[] b = new Bytes32[1];
            byte[] productId= Numeric.hexStringToByteArray("0x3b49b3ad39e7ea5eda088f33a2da743e52642e41c0948bf05787743534b32556");
            b[0]=new Bytes32(productId);

            final Function function = new Function(
                    "clearCampaign",
                    Collections.<Type>emptyList(),
                    Collections.<TypeReference<?>>emptyList());

            String encodedFunction = FunctionEncoder.encode(function);

            // Create new Transaction
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GASPRICE, GASLIMIT, CAMPAIGN_CONTRACT, BigInteger.ZERO, encodedFunction);
            Credentials credentials=Credentials.create(PRIVATE_KEY);

            log.info("getAddress: "+credentials.getAddress());
            // Sign the Transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            PersonalUnlockAccount personalUnlockAccount = null;

            personalUnlockAccount=web3j.personalUnlockAccount("0x06c1604d4277c6E845f375EC1C6F6c1feAAf4890","").send();

            log.info("accountUnlocked : "+personalUnlockAccount.accountUnlocked());

            EthSendTransaction t = web3j.ethSendTransaction(Transaction.createFunctionCallTransaction(
                    PUBLIC_KEY,
                    nonce, //or nullL
                    GASPRICE, //gasPrice
                    GASLIMIT, //gasLimit
                    CAMPAIGN_CONTRACT,
                    encodedFunction)).send();

            // Send the Transaction
            // EthSendTransaction t=  web3j.ethSendRawTransaction(hexValue).send();

            EthGetTransactionReceipt r = web3j.ethGetTransactionReceipt(t.getTransactionHash()).send();
            if(t!=null){
                ObjectMapper mapper = new ObjectMapper();
                String ethSendTransactionLog=mapper.writeValueAsString(t);
                log.debug("ethSendTransactionLog : "+ethSendTransactionLog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // String ver = web3ClientVersion ==null ? "null" : web3ClientVersion.getWeb3ClientVersion();

        log.info("The time is now {}", dateFormat.format(new Date())+" / "+nonce);





        return "index";
    }


}
