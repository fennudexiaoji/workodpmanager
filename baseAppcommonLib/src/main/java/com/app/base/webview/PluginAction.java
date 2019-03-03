package com.app.base.webview;

/**
 * Created by 7du-28 on 2017/6/19.
 */

public class PluginAction {

    public static final String ACTION = "call";
    public static final String ACTION_CREATE_NEW_WINDOW = "createNewWindow";//openNewWindow
    public static final String ACTION_OPEN_NEW_WINDOW="openNewWindow";
    public static final String ACTION_LOGIN = "loginSuccess";
    public static final String ACTION_TAKE_LOCAL_USER_LIST = "takeLocalUserList";
    public static final String ACTION_DELETE_USER_ACCOUNT_BY_ID = "deleteUserAccountById";
    public static final String ACTION_SHOW_PHONE_INPUT_EDIT = "showPhoneInputEdit";

    public static final String ACTION_GET_GENERATE_DEVICE_UNIQUE_ID = "getGenerateDeviceUniqueId";
    public static final String LOGIN_OUT="loginOut";

    public static final String ACTION_FINISH_ACTIVITY = "finishCurrentActivity";
    //后退一步
    public static final String ACTION_BACK_PRESS = "webGoBackPagePress";

    public static final String ACTION_PUT_DATA_FOR_LAST_PAGE = "putDataForLastPage";

    public static final String ACTION_SHARE_TO_THIRD_APP = "shareToThirdApp";
    public static final String ACTION_WX_AUTHORIZATION_LOGIN = "wxAuthorizationLogin";

    public static final String ACTION_OPEN_NEAR_STORE_LIST = "openNearStoreList";

    public static final String ACTION_OPEN_CAMERA_TAKE_PHOTO = "openCameraTokePhoto";//打开相机拍照
    public static final String ACTION_OPEN_PHOTO_TAKE_PHOTO = "openPhotoTokePhoto";//打开相册

    public static final String ACTION_ADDRESS_LOCATION = "addressLocation";//地址选择

    public static final String ACTION_RELOAD_LAST_PAGE = "reloadLastPage";//刷新链接

    public static final String ACTION_LOCATION_CURRENT_POSITION = "locationCurrentPosition";//定位当前位置

    public static final String ACTION_OPEN_STORE_LOCATION = "openStoreLocation";//查看定位店铺位置

    public static final String ACTION_SHOW_TIME_PICKER = "showTimePicker";//时间选择



    public static final String ACTION_FRANCHISEES_CUSTOMER_ACREAGE_FLOOR = "franchiseesCustomerAcreageFloor";//加盟选择人流量面积楼层

    public static final String ACTION_SHOW_WHEEL_VIEW_REASON_LIST = "showWheelViewReasonList";//拨轮控件显示列表

    public static final String ACTION_QR_CODE_SCAN="qrCodeScan";//扫描二维码条形码

    public static final String ACTION_BUSINESS_LICENCE_TERM_OF_VALIDITY="businessLicenceTermOfValidity";//营业执照有效期选择
    public static final String ACTION_CREDENTIALS_UPLOAD="credentialsUpload";

    public static final String ACTION_CHECK_APP_VERSION="checkAppVersion";

    public static final String ACTION_COPY_TO_CLIPBOARD="copy2clipboard";

    public static final String ACTION_SHOW_INPUT_DIALOG_FOR_PRODUCTS_PRICE = "showInputDialogForProductPrice";//价格输入

    public static final String ACTION_CLEAR_WEB_VIEW_CACHE="clearWebViewCache";
}
