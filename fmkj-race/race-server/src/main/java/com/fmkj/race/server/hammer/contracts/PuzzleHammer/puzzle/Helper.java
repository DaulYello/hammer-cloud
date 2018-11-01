package com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle;


import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.fmkj.race.server.hammer.contracts.PuzzleHammer.PuzzleHammer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;


public class Helper {
    /**
     * 钱包文件密码
     */
    private final String password;
    /**
     * 钱包文件名及路径
     */
    private final String walletfile;
    /**
     * http方式，以太坊IP地址
     */
    private final String ip;
    /**
     * http方式，端口号
     */
    private final String port;
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(Helper.class);
    /**
     * web3j实例
     */
    private Web3j web3j = null;
    /**
     * 签名认证实例
     */
    private Credentials credentials = null;
    /**
     * 竞锤合约实例
     */
    private PuzzleHammer puzzleHammer = null;

    /**
     * 默认生成Helper实例
     */
    public Helper() {
        this.password = "11111111";
        this.walletfile = "C:\\swanchain\\data\\00\\keystore\\UTC--2018-10-19T03-47-19.803792500Z--4aef396f4f43ebbf7e47beeff8d3cf402b2ff8ad";
        this.ip = "localhost";
        this.port = "9797";
    }

    /**
     * 通过参数，生成新的Helper实例
     *
     * @param password   钱包文件密码
     * @param walletfile 钱包文件名及路径
     * @param ip         http方式，以太坊IP地址
     * @param port       http方式，端口号
     */
    public Helper(String password, String walletfile, String ip, String port) {
        this.password = password;
        this.walletfile = walletfile;
        // defaults to http://localhost:8545/
        this.ip = ip;
        this.port = port;
    }

    /**
     *
     * @return 成功初始化实例属性后，返回true
     */
    public boolean init() {
        boolean bl = false;
        this.web3j = Web3j.build(new HttpService("http://" + this.ip + ":" + this.port));
        try {
            log.info("【Web3j】版本号Connected to Ethereum client version: "
                    + this.web3j.web3ClientVersion().send().getWeb3ClientVersion());
            this.credentials = WalletUtils.loadCredentials(this.password, this.walletfile);
            log.info("【Web3j】credentials to load success......");
            bl = true;
        } catch (IOException e) {
            log.error("【Web3j-Failed】IO异常Credentials failed to load. ");
            e.printStackTrace();
        } catch (CipherException e) {
            log.error("【Web3j-Failed】Credentials failed to load. ");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("【Web3j-Failed】Failed to get Ethereum client version.");
            e.printStackTrace();
        } finally {
            return bl;
        }
    }

    /**
     * 释放实例资源
     */
    public void release() {
        if (this.web3j != null) {
            this.web3j.shutdown();
        }
    }

    /**
     * 发布竞锤合约
     * @param info 竞锤合约活动信息
     * @param origPerson 活动发起人
     * @return 合约地址
     */
    public String deployContract(Info info, Person origPerson) {

        String puzzleHammerAddress = null;

        try {
            log.info("Deploying smart contract");
            this.puzzleHammer = PuzzleHammer.deploy(
                    this.web3j, this.credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                    info.getcId(), info.getcType(), info.gettType(), info.getcLimit(),
                    origPerson.getName(), origPerson.getID()).send();
            puzzleHammerAddress = this.puzzleHammer.getContractAddress();
            log.info("Smart contract deployed to address " + puzzleHammerAddress);
        } catch (Exception e) {
            log.error("Failed to deploy smart contract. ");
        } finally {
            return puzzleHammerAddress;
        }

    }

    /**
     * 加载以太坊上的竞锤合约
     * @param contractAddress 竞锤合约地址
     * @return 成功加载以后，返回true
     */
    public boolean loadContract(String contractAddress) {
        boolean ret = false;
        this.puzzleHammer = null;
        this.puzzleHammer = PuzzleHammer.load(contractAddress, this.web3j, this.credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
        try {
            if (this.puzzleHammer != null && this.puzzleHammer.isValid()) {
                log.info("Smart contract loaded at address " + contractAddress);
                ret = true;
            } else {
                this.puzzleHammer = null;
                log.error("Failed to load smart contract. ");
            }
        } catch (IOException e) {
            this.puzzleHammer = null;
            log.error("the contract bytecode does not match what's deployed at the provided address "
                    + contractAddress);
        } finally {
            return ret;
        }

    }

    /**
     *获得竞锤活动优胜者
     * @return 竞锤活动优胜者
     */
    public Person getWinner() {
        Person winner = null;
        try {
            Tuple2<String, BigInteger> tpWinner = this.puzzleHammer.getWinner().send();
            if (tpWinner.getValue1() == null || tpWinner.getValue1().equals("") || tpWinner.getValue2().intValue() == 0) {
                throw (new Exception("Bad winner value. "));
            }
            winner = new Person(tpWinner.getValue1(), tpWinner.getValue2());

        } catch (Exception e) {
            log.error("Failed to getWinner. ");
        } finally {
            return winner;
        }
    }

    /**
     * 获得竞锤活动发起人
     * @return 竞锤活动发起人
     */
    public Person getOriginator() {
        Person originator = null;
        try {
            Tuple2<String, BigInteger> tpOriginator = this.puzzleHammer.getOriginator().send();
            originator = new Person(tpOriginator.getValue1(), tpOriginator.getValue2());

        } catch (Exception e) {
            log.error("Failed to getOriginator. ");
        } finally {
            return originator;
        }
    }

    /**
     * 获得活动参与总次数
     * @return 活动参与总次数
     */
    public BigInteger getTotleNumParticipants() {
        BigInteger totleNumParticipants = null;
        try {
            totleNumParticipants = this.puzzleHammer.getTotleNumParticipants().send();
        } catch (Exception e) {
            log.error("Failed to getTotleNumParticipants. ");
        } finally {
            return totleNumParticipants;
        }
    }

    /**
     * 获得竞锤活动状态
     * @return 竞锤活动状态
     */
    public BigInteger getState() {
        BigInteger state = null;
        try {
            state = this.puzzleHammer.getState().send();
        } catch (Exception e) {
            log.error("Failed to getState. ");
        } finally {
            return state;
        }
    }

    /**
     * 获得竞锤活动优胜者算法种子
     * @return 竞锤活动优胜者算法种子
     */
    public BigInteger getSeed() {
        BigInteger seed = null;
        try {
            seed = this.puzzleHammer.getSeed().send();
        } catch (Exception e) {
            log.error("Failed to getSeed. ");
        } finally {
            return seed;
        }
    }

    /**
     * 获得竞锤活动信息
     * @return 竞锤活动信息
     */
    public Info getInfo() {
        Info info = null;
        try {
            Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> tpInfo = this.puzzleHammer.getInfo().send();
            info = new Info(tpInfo.getValue1(), tpInfo.getValue2(), tpInfo.getValue3(), tpInfo.getValue4());

        } catch (Exception e) {
            log.error("Failed to getInfo. ");
        } finally {
            return info;
        }
    }

    /**
     * 改变竞锤活动状态
     * @param state 新的竞锤活动状态
     * @return 成功改变竞锤活动状态后，返回true
     */
    public boolean changeStage(State state) {
        boolean ret = false;
        try {
            TransactionReceipt transactionReceipt = this.puzzleHammer.changeStage(BigInteger.valueOf(state.ordinal())).send();
            List<PuzzleHammer.ChangeEventResponse> listChangeEvent = this.puzzleHammer.getChangeEvents(transactionReceipt);
            for (PuzzleHammer.ChangeEventResponse event : listChangeEvent) {
                if (event.next.intValue() == state.ordinal()) {
                    ret = true;
                }
            }
        } catch (Exception e) {
            log.error("Failed to changeStage. ");
        } finally {
            return ret;
        }

    }

    /**
     * 参与竞锤活动
     * @param particiPerson 参与人
     * @return 成功参与竞锤活动后，返回true
     */
    public boolean particiPuzzle(Person particiPerson) {
        boolean ret = false;
        try {
            if (particiPerson.getName() == null || particiPerson.getName().equals("") || particiPerson.getID().intValue() == 0) {
                throw (new Exception("Bad peron value. "));
            }
            TransactionReceipt transactionReceipt = this.puzzleHammer.particiPuzzle(particiPerson.getName(), particiPerson.getID()).send();
            List<PuzzleHammer.ParticipateEventResponse> listParticipateEvent = this.puzzleHammer.getParticipateEvents(transactionReceipt);
            for (PuzzleHammer.ParticipateEventResponse event : listParticipateEvent) {
                if (event.name.equals(particiPerson.getName()) && event.id.equals(particiPerson.getID())) {
                    ret = true;
                }
            }
        } catch (Exception e) {
            log.error("Failed to particiPuzzle. ");
        } finally {
            return ret;
        }
    }

    /**
     * 竞锤活动优胜者算法
     * @return 成功产生出优胜者后，返回true
     */
    public boolean puzzleWinner() {
        boolean seedRet = false;
        boolean winnerRet = false;
        try {
            TransactionReceipt transactionReceipt = this.puzzleHammer.puzzleWinner().send();

            List<PuzzleHammer.SelectEventResponse> listSelectEvent = this.puzzleHammer.getSelectEvents(transactionReceipt);
            for (PuzzleHammer.SelectEventResponse event : listSelectEvent) {
                if (event.seed.intValue() != 0) {
                    seedRet = true;
                }
            }

            List<PuzzleHammer.WinEventResponse> listWinEvent = this.puzzleHammer.getWinEvents(transactionReceipt);
            for (PuzzleHammer.WinEventResponse event : listWinEvent) {
                if (event.id.intValue() != 0 && event.name != null) {
                    winnerRet = true;
                }
            }
        } catch (Exception e) {
            log.error("Failed to puzzleWinner. ");
        } finally {
            return seedRet && winnerRet;
        }
    }
    
}
