package com.daun.donate.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
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

@Component
public class ScheduledTasks {

    private static String CAMPAIGN_CONTRACT="0x994923AA1da3FC423Cd811aE97737DD7076C9623";
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
        BigInteger nonce=null;
        Web3ClientVersion web3ClientVersion = null;
        try {
            /*TransactionManager

            GAS_PRICE,
                    GAS_LIMIT,
                    contractAddress,
                    encodedFunction*/
/*
            Transaction transaction = Transaction.createEthCallTransaction(
                    CAMPAIGN_CONTRACT,
                    nonce,
                    GAS_PRICE,
                    GAS_LIMIT,
                    BigInteger.ZERO,
                    getGreeterSolidityBinary() + encodedConstructor);

            org.web3j.protocol.core.methods.response.EthSendTransaction
                    transactionResponse = web3j.ethSendTransaction(transaction)
                    .sendAsync().get();*/



            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(PUBLIC_KEY, DefaultBlockParameterName.LATEST).send();
            nonce = ethGetTransactionCount.getTransactionCount();
            nonce.add(BigInteger.valueOf(1));
// Encode the function
            Bytes32[] b = new Bytes32[1];
            byte[] productId=Numeric.hexStringToByteArray("0x392d7aab8cbed12e58f94fe185e2e589de8e3fe7e295fbde35671c99cb259f18");
            b[0]=new Bytes32(productId);

/*
            address _targetAddress,
            string memory _name,
            string memory _thumbnail,
            uint _cap,
            uint _startTime,
            uint _endTime,
            string memory _contents,
                    bytes32[] memory _productIdList*/

            /*final Function function = new Function(
                    "addCampaign",
                    Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(PUBLIC_KEY),
                            new org.web3j.abi.datatypes.Utf8String("테스트2"),
                            new org.web3j.abi.datatypes.Utf8String("테스트3"),
                            new org.web3j.abi.datatypes.Uint(BigInteger.valueOf(100)),
                            new org.web3j.abi.datatypes.Uint(BigInteger.valueOf(100)),
                            new org.web3j.abi.datatypes.Uint(BigInteger.valueOf(100)),
                            new org.web3j.abi.datatypes.Utf8String("테스트"),
                            new Bytes32(productId)),
                    Collections.<TypeReference<?>>emptyList());*/

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
            log.info("getError  : "+t.getError().getData()+" / "+t.getError().getMessage() +" / "+t.getError().getCode() );
            log.info("getResult  : "+t.getResult());
            log.info("getTransactionHash : "+t.getTransactionHash());


        } catch (Exception e) {
            e.printStackTrace();
        }
       // String ver = web3ClientVersion ==null ? "null" : web3ClientVersion.getWeb3ClientVersion();

        log.info("The time is now {}", dateFormat.format(new Date())+" / "+nonce);
    }


}