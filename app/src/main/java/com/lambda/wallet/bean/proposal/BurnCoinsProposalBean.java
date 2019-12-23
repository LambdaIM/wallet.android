package com.lambda.wallet.bean.proposal;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 16:22
 */
public class BurnCoinsProposalBean {


    /**
     * title : Lambda team burns 700 million LAMB team tokens to support Lambda ecosystem growth 销毁Lambda研发团队持有的7亿LAMB
     * description : Based original token structure plan, Lambda team was allocated 1 billion LAMB tokens.Lambda team believes in the community and long term ecosystem growth and hence have decided to burn 700 million Lambda tokens and the remaining 300 million tokens will be unlocked Linearly in 10 years.Lambda team will keep the original intention and make unremitting efforts to realize the great vision of decentralized value Internet which would truly make the world a better place. Lambda研发团队原持有10亿LAMB，现销毁7亿LAMB,剩余3亿LAMB，10年时间线性解锁。去中心化存储技术的发展和进步虽面临众多挑战但却充满光明前景， Lambda基金会和研发团队将不忘初心， 为新一代数字革命和价值互联网实现的伟大愿景而不懈努力。
     * burn_amount : [{"denom":"ulamb","amount":"700000000000000"}]
     */

    private String title;
    private String description;
    private List<BurnAmountBean> burn_amount;

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

    public List<BurnAmountBean> getBurn_amount() {
        return burn_amount;
    }

    public void setBurn_amount(List<BurnAmountBean> burn_amount) {
        this.burn_amount = burn_amount;
    }

    public static class BurnAmountBean {
        /**
         * denom : ulamb
         * amount : 700000000000000
         */

        private String denom;
        private String amount;

        public String getDenom() {
            return denom;
        }

        public void setDenom(String denom) {
            this.denom = denom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
