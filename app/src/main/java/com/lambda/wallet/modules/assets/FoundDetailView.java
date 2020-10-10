package com.lambda.wallet.modules.assets;

import com.lambda.wallet.bean.FundInfoMineBean;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.HomeAssetBean;
import com.lambda.wallet.bean.PreMiningDetail;

public interface FoundDetailView {

    void getAssetDetailsDataHttp(HomeAssetBean assetBean);

    void getFundDetailsDataHttp(PreMiningDetail assetBean);

    void getFundInfoMineDataHttp(FundInfoMineBean assetBean);



    void getMineGasDataHttp(GasBean gasBean);

    void getFundInfoMineDataHttpError();

}
