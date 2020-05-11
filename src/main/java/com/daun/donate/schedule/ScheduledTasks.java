package com.daun.donate.schedule;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tuples.Tuple;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static javax.swing.UIManager.get;

@Component
public class ScheduledTasks {

    private static String CAMPAIGN_CONTRACT="0xBE19E19e96A6A8ff3c51588f9799F56206178d73";
    private static BigInteger GASPRICE=BigInteger.valueOf(Long.parseLong("20000000000"));
    private static BigInteger GASLIMIT=BigInteger.valueOf(Long.parseLong("531717"));
    private static String PUBLIC_KEY="0x06c1604d4277c6E845f375EC1C6F6c1feAAf4890";
    private static String PRIVATE_KEY="fafa80853dd96a9382368940a4b899aa63ef221e741597ff20d78c2b87649361";



    @Autowired
    private Admin web3j;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 100000)
    public void reportCurrentTime(){
        getCampaignList();
    }
    private Object getCampaignList(){
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
                List<BigInteger> tArray = (List<BigInteger>) decode1.get(0).getValue();
                for(BigInteger d:tArray){
                    System.out.println("getValue1 tArray = " + d);
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
        return new Object();
    }
    private void deleteCampaign(int campaignIdx){
        BigInteger nonce=null;
        Web3ClientVersion web3ClientVersion = null;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(PUBLIC_KEY, DefaultBlockParameterName.LATEST).send();
            nonce = ethGetTransactionCount.getTransactionCount();
            nonce.add(BigInteger.valueOf(1));

            Bytes32[] b = new Bytes32[1];
            byte[] productId=Numeric.hexStringToByteArray("0x3b49b3ad39e7ea5eda088f33a2da743e52642e41c0948bf05787743534b32556");
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
    }

}