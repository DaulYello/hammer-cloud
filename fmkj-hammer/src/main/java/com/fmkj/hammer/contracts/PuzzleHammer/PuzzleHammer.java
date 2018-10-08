package com.fmkj.hammer.contracts.PuzzleHammer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class PuzzleHammer extends Contract {
    private static final String BINARY = "0x608060405260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005157600080fd5b50604051610fae380380610fae833981018060405281019080805190602001909291908051906020019092919080519060200190929190805190602001909291908051820192919060200180519060200190929190505050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550856001600001819055508460018001819055508360016002018190555082600160030181905550604080519081016040528083815260200182815250600560008201518160000190805190602001906101439291906101a3565b50602082015181600101559050506000600b819055506000600c60006101000a81548160ff0219169083600581111561017857fe5b02179055506000600c60016101000a81548160ff021916908315150217905550505050505050610248565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106101e457805160ff1916838001178555610212565b82800160010185558215610212579182015b828111156102115782518255916020019190600101906101f6565b5b50905061021f9190610223565b5090565b61024591905b80821115610241576000816000905550600101610229565b5090565b90565b610d57806102576000396000f300608060405260043610610099576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806315c8499e1461009e5780631865c57d1461013557806339e7357c1461016e578063591d264a146101995780635a9b0b89146101c45780636516c440146102045780638e7ea5b21461028f578063b9172dec14610326578063fde314991461036e575b600080fd5b3480156100aa57600080fd5b506100b361039d565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156100f95780820151818401526020810190506100de565b50505050905090810190601f1680156101265780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561014157600080fd5b5061014a610450565b6040518082600581111561015a57fe5b60ff16815260200191505060405180910390f35b34801561017a57600080fd5b50610183610467565b6040518082815260200191505060405180910390f35b3480156101a557600080fd5b506101ae6104a7565b6040518082815260200191505060405180910390f35b3480156101d057600080fd5b506101d96104eb565b6040518085815260200184815260200183815260200182815260200194505050505060405180910390f35b34801561021057600080fd5b50610275600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610516565b604051808215151515815260200191505060405180910390f35b34801561029b57600080fd5b506102a46106f1565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156102ea5780820151818401526020810190506102cf565b50505050905090810190601f1680156103175780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34801561033257600080fd5b50610354600480360381019080803560ff1690602001909291905050506107da565b604051808215151515815260200191505060405180910390f35b34801561037a57600080fd5b50610383610905565b604051808215151515815260200191505060405180910390f35b606060006005600001600560010154818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104415780601f1061041657610100808354040283529160200191610441565b820191906000526020600020905b81548152906001019060200180831161042457829003601f168201915b50505050509150915091509091565b6000600c60009054906101000a900460ff16905090565b6000600580600581111561047757fe5b600c60009054906101000a900460ff16600581111561049257fe5b14151561049e57600080fd5b600b5491505090565b600060028060058111156104b757fe5b600c60009054906101000a900460ff1660058111156104d257fe5b101515156104df57600080fd5b60098054905091505090565b6000806000806001600001546001800154600160020154600160030154935093509350935090919293565b6000610520610be5565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561057b57600080fd5b600180600581111561058957fe5b600c60009054906101000a900460ff1660058111156105a457fe5b1415156105b057600080fd5b6040805190810160405280868152602001858152509150600982908060018154018082558091505090600182039060005260206000209060020201600090919290919091506000820151816000019080519060200190610611929190610bff565b50602082015181600101555050506001600a6000868152602001908152602001600020600082825401925050819055507f2756c26bac84723261f979038b47d60558a8822014552bd1730b437e4712e84985856040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156106aa57808201518184015260208101905061068f565b50505050905090810190601f1680156106d75780820380516001836020036101000a031916815260200191505b50935050505060405180910390a160019250505092915050565b60606000600580600581111561070357fe5b600c60009054906101000a900460ff16600581111561071e57fe5b14151561072a57600080fd5b6007600001600760010154818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107ca5780601f1061079f576101008083540402835291602001916107ca565b820191906000526020600020905b8154815290600101906020018083116107ad57829003601f168201915b5050505050915092509250509091565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561083857600080fd5b600c60009054906101000a900460ff16600581111561085357fe5b83600581111561085f57fe5b11151561086b57600080fd5b600c60009054906101000a900460ff16905082600c60006101000a81548160ff0219169083600581111561089b57fe5b02179055507fe6a8b2921e33fb37019c74b37bc7e69ec0fe79105f472760e81cccc48259daf68184604051808360058111156108d357fe5b60ff1681526020018260058111156108e757fe5b60ff1681526020019250505060405180910390a16001915050919050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561096357600080fd5b60001515600c60019054906101000a900460ff16151514151561098557600080fd5b600380600581111561099357fe5b600c60009054906101000a900460ff1660058111156109ae57fe5b1415156109ba57600080fd5b600980549050600160030154111515156109d357600080fd5b60098054905091506109e3610b7e565b600b819055507f03cd386ada9c75c1f30443943481e769151f2336421ebdadb5ef6d527d4675fb600b546040518082815260200191505060405180910390a1600982600b54811515610a3157fe5b06815481101515610a3e57fe5b9060005260206000209060020201600760008201816000019080546001816001161561010002031660029004610a75929190610c7f565b50600182015481600101559050507faa280eabbed26daa2c184e9738f29212898525156e693a9a755d60113e96dd8b60076000016007600101546040518080602001838152602001828103825284818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015610b3e5780601f10610b1357610100808354040283529160200191610b3e565b820191906000526020600020905b815481529060010190602001808311610b2157829003601f168201915b5050935050505060405180910390a160019250506000600b54141515610b7a576001600c60016101000a81548160ff0219169083151502179055505b5090565b6000806000600980549050915081440182420160405180838152602001828152602001925050506040518091039020600190049050448101824442010160405180838152602001828152602001925050506040518091039020600190049050809250505090565b604080519081016040528060608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c4057805160ff1916838001178555610c6e565b82800160010185558215610c6e579182015b82811115610c6d578251825591602001919060010190610c52565b5b509050610c7b9190610d06565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610cb85780548555610cf5565b82800160010185558215610cf557600052602060002091601f016020900482015b82811115610cf4578254825591600101919060010190610cd9565b5b509050610d029190610d06565b5090565b610d2891905b80821115610d24576000816000905550600101610d0c565b5090565b905600a165627a7a7230582004a76cd90634f6547a3ce1259b317018e4613f7da0e61ffa994b43fce340dfc30029";

    public static final String FUNC_GETWINNER = "getWinner";

    public static final String FUNC_GETORIGINATOR = "getOriginator";

    public static final String FUNC_GETTOTLENUMPARTICIPANTS = "getTotleNumParticipants";

    public static final String FUNC_GETSTATE = "getState";

    public static final String FUNC_GETSEED = "getSeed";

    public static final String FUNC_GETINFO = "getInfo";

    public static final String FUNC_CHANGESTAGE = "changeStage";

    public static final String FUNC_PARTICIPUZZLE = "particiPuzzle";

    public static final String FUNC_PUZZLEWINNER = "puzzleWinner";

    public static final Event CHANGE_EVENT = new Event("Change",
            Arrays.asList(),
            Arrays.asList(new TypeReference<Uint8>() {
            }, new TypeReference<Uint8>() {
            }));

    public static final Event SELECT_EVENT = new Event("Select",
            Arrays.asList(),
            Arrays.asList(new TypeReference<Uint256>() {
            }));

    public static final Event WIN_EVENT = new Event("Win",
            Arrays.asList(),
            Arrays.asList(new TypeReference<Utf8String>() {
            }, new TypeReference<Uint256>() {
            }));

    public static final Event PARTICIPATE_EVENT = new Event("Participate",
            Arrays.asList(),
            Arrays.asList(new TypeReference<Utf8String>() {
            }, new TypeReference<Uint256>() {
            }));

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    protected PuzzleHammer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PuzzleHammer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RemoteCall<PuzzleHammer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger cId, BigInteger cType, BigInteger tType, BigInteger cLimit, String origName, BigInteger origID) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new Uint256(cId),
                new Uint256(cType),
                new Uint256(tType),
                new Uint256(cLimit),
                new Utf8String(origName),
                new Uint256(origID)));
        return deployRemoteCall(PuzzleHammer.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<PuzzleHammer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger cId, BigInteger cType, BigInteger tType, BigInteger cLimit, String origName, BigInteger origID) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new Uint256(cId),
                new Uint256(cType),
                new Uint256(tType),
                new Uint256(cLimit),
                new Utf8String(origName),
                new Uint256(origID)));
        return deployRemoteCall(PuzzleHammer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<ChangeEventResponse> getChangeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CHANGE_EVENT, transactionReceipt);
        ArrayList<ChangeEventResponse> responses = new ArrayList<ChangeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ChangeEventResponse typedResponse = new ChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.prev = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.next = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeEventResponse> changeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeEventResponse>() {
            @Override
            public ChangeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGE_EVENT, log);
                ChangeEventResponse typedResponse = new ChangeEventResponse();
                typedResponse.log = log;
                typedResponse.prev = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.next = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ChangeEventResponse> changeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGE_EVENT));
        return changeEventObservable(filter);
    }

    public List<SelectEventResponse> getSelectEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SELECT_EVENT, transactionReceipt);
        ArrayList<SelectEventResponse> responses = new ArrayList<SelectEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SelectEventResponse typedResponse = new SelectEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seed = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SelectEventResponse> selectEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SelectEventResponse>() {
            @Override
            public SelectEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SELECT_EVENT, log);
                SelectEventResponse typedResponse = new SelectEventResponse();
                typedResponse.log = log;
                typedResponse.seed = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SelectEventResponse> selectEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SELECT_EVENT));
        return selectEventObservable(filter);
    }

    public List<WinEventResponse> getWinEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(WIN_EVENT, transactionReceipt);
        ArrayList<WinEventResponse> responses = new ArrayList<WinEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WinEventResponse typedResponse = new WinEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WinEventResponse> winEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, WinEventResponse>() {
            public WinEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(WIN_EVENT, log);
                WinEventResponse typedResponse = new WinEventResponse();
                typedResponse.log = log;
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<WinEventResponse> winEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WIN_EVENT));
        return winEventObservable(filter);
    }

    public List<ParticipateEventResponse> getParticipateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(PARTICIPATE_EVENT, transactionReceipt);
        ArrayList<ParticipateEventResponse> responses = new ArrayList<ParticipateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ParticipateEventResponse typedResponse = new ParticipateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ParticipateEventResponse> participateEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ParticipateEventResponse>() {
            @Override
            public ParticipateEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PARTICIPATE_EVENT, log);
                ParticipateEventResponse typedResponse = new ParticipateEventResponse();
                typedResponse.log = log;
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ParticipateEventResponse> participateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PARTICIPATE_EVENT));
        return participateEventObservable(filter);
    }

    public RemoteCall<Tuple2<String, BigInteger>> getWinner() {
        final Function function = new Function(FUNC_GETWINNER,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteCall<Tuple2<String, BigInteger>>(
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<Tuple2<String, BigInteger>> getOriginator() {
        final Function function = new Function(FUNC_GETORIGINATOR,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteCall<Tuple2<String, BigInteger>>(
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getTotleNumParticipants() {
        final Function function = new Function(FUNC_GETTOTLENUMPARTICIPANTS,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getState() {
        final Function function = new Function(FUNC_GETSTATE,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getSeed() {
        final Function function = new Function(FUNC_GETSEED,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> getInfo() {
        final Function function = new Function(FUNC_GETINFO,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> changeStage(BigInteger s) {
        final Function function = new Function(
                FUNC_CHANGESTAGE,
                Arrays.asList(new Uint8(s)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> particiPuzzle(String Name, BigInteger ID) {
        final Function function = new Function(
                FUNC_PARTICIPUZZLE,
                Arrays.asList(new Utf8String(Name),
                        new Uint256(ID)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> puzzleWinner() {
        final Function function = new Function(
                FUNC_PUZZLEWINNER,
                Arrays.asList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static PuzzleHammer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PuzzleHammer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static PuzzleHammer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PuzzleHammer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class ChangeEventResponse {
        public Log log;

        public BigInteger prev;

        public BigInteger next;
    }

    public static class SelectEventResponse {
        public Log log;

        public BigInteger seed;
    }

    public static class WinEventResponse {
        public Log log;

        public String name;

        public BigInteger id;
    }

    public static class ParticipateEventResponse {
        public Log log;

        public String name;

        public BigInteger id;
    }
}
