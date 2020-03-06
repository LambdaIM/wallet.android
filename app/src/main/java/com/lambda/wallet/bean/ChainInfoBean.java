package com.lambda.wallet.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2020/3/6
 * Time: 13:10
 */
public class ChainInfoBean {

    /**
     * protocol_version : {"p2p":"7","block":"10","app":"0"}
     * id : c4bc13daac7b6750eaeacc3be408b3bfae00935e
     * listen_addr : tcp://0.0.0.0:26656
     * network : lambda-chain-3.0
     * version : 0.31.5
     * channels : 4020212223303800
     * moniker : cv-moniker-2
     * other : {"tx_index":"on","rpc_address":"tcp://0.0.0.0:26657"}
     */

    private ProtocolVersionBean protocol_version;
    private String id;
    private String listen_addr;
    private String network;
    private String version;
    private String channels;
    private String moniker;
    private OtherBean other;

    public ProtocolVersionBean getProtocol_version() {
        return protocol_version;
    }

    public void setProtocol_version(ProtocolVersionBean protocol_version) {
        this.protocol_version = protocol_version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListen_addr() {
        return listen_addr;
    }

    public void setListen_addr(String listen_addr) {
        this.listen_addr = listen_addr;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public static class ProtocolVersionBean {
        /**
         * p2p : 7
         * block : 10
         * app : 0
         */

        private String p2p;
        private String block;
        private String app;

        public String getP2p() {
            return p2p;
        }

        public void setP2p(String p2p) {
            this.p2p = p2p;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }
    }

    public static class OtherBean {
        /**
         * tx_index : on
         * rpc_address : tcp://0.0.0.0:26657
         */

        private String tx_index;
        private String rpc_address;

        public String getTx_index() {
            return tx_index;
        }

        public void setTx_index(String tx_index) {
            this.tx_index = tx_index;
        }

        public String getRpc_address() {
            return rpc_address;
        }

        public void setRpc_address(String rpc_address) {
            this.rpc_address = rpc_address;
        }
    }
}
