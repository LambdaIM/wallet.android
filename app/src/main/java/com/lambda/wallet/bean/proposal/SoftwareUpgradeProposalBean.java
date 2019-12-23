package com.lambda.wallet.bean.proposal;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 16:24
 */
public class SoftwareUpgradeProposalBean {

    /**
     * title : Software Upgrade
     * description : Upgrade to new version
     * version : 1
     * software : https://github.com/LambdaIM/launch/releases/tag/v0.3.0
     * switch_height : 1000
     * threshold : 0.800000000000000000
     */

    private String title;
    private String description;
    private String version;
    private String software;
    private String switch_height;
    private String threshold;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getSwitch_height() {
        return switch_height;
    }

    public void setSwitch_height(String switch_height) {
        this.switch_height = switch_height;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}
