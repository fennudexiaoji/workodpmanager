
    /*window.callActivityPlugin = function(callbackSuccess,str) {
			cordova.exec(callbackSuccess, null, "CallActivityPlugin", "call", [ str ]);
		};*/
		//android端返回的error回调
		/*var pluginFailed = function(message) {
			alert("failed>>" + message);
		}
		var pluginSuccess=function(echoValue) {
				deviceNumberStr=echoValue;
		}*/
    //打开新页面
    var createNewWindow=function (str){
    	    var onDeviceReady = function() {
                 cordova.exec(null, null, "MainCordovaActivityPlugin", "createNewWindow", [str]);
            };
    	    document.addEventListener("deviceready", onDeviceReady, true);
    }
    //含fragment中的activity打开新的
    var openNewWindow=function (str){
        	    var onDeviceReady = function() {
                     cordova.exec(null, null, "MainCordovaActivityPlugin", "openNewWindow", [str]);
                };
        	    document.addEventListener("deviceready", onDeviceReady, true);
        }
    //重新刷新页面
    var reloadLastPage=function (){
        var onDeviceReady = function() {
             cordova.exec(null, null, "MainCordovaActivityPlugin", "reloadLastPage", [""]);
        };
        document.addEventListener("deviceready", onDeviceReady, true);
    }
    //登陆成功
	var loginSuccess=function (str){
	    var onDeviceReady = function() {
	          /*var callbackSuccess=function(message){
	            alert(message);
	          }*/
             cordova.exec(null, null, "MainCordovaActivityPlugin", "loginSuccess", [str]);
        };
	    document.addEventListener("deviceready", onDeviceReady, true);
	}
    //获取账号
    var takeLocalUserList=function (callbackSuccess){
            var onDeviceReady = function() {
                 cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "takeLocalUserList", [""]);
            };
            document.addEventListener("deviceready", onDeviceReady, true);
        }
     //删除单个账号信息
    var deleteUserAccountById=function (obj){
        var onDeviceReady = function() {
             cordova.exec(null, null, "MainCordovaActivityPlugin", "deleteUserAccountById", [obj]);
        };
        document.addEventListener("deviceready", onDeviceReady, true);
    }
	var loginOut=function (){
    	    var onDeviceReady = function() {
                 cordova.exec(null, null, "MainCordovaActivityPlugin", "loginOut", [""]);
            };
    	    document.addEventListener("deviceready", onDeviceReady, true);
    	}
    var finishCurrentActivity=function (){
    	    var onDeviceReady = function() {
                 cordova.exec(null, null, "MainCordovaActivityPlugin", "finishCurrentActivity", [""]);
            };
    	    document.addEventListener("deviceready", onDeviceReady, true);
    }
    //js调用app新版本检测
    var checkAppVersion=function (){
    	    var onDeviceReady = function() {
                 cordova.exec(null, null, "MainCordovaActivityPlugin", "checkAppVersion", [""]);
            };
    	    document.addEventListener("deviceready", onDeviceReady, true);
    }
    //后退一页 或关闭当前页面
    var webGoBackPagePress=function (){
    	    var onDeviceReady = function() {
                 cordova.exec(null, null, "MainCordovaActivityPlugin", "webGoBackPagePress", [""]);
            };
    	    document.addEventListener("deviceready", onDeviceReady, true);
    }

    //获取设备id
    var getGenerateDeviceUniqueId=function (callbackSuccess){
        var onDeviceReady = function() {
             cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "getGenerateDeviceUniqueId", [""]);
        };
        document.addEventListener("deviceready", onDeviceReady, true);
    }
    //分享
    var shareToThirdApp=function(str){
        var onDeviceReady = function() {
                  cordova.exec(null, null, "MainCordovaActivityPlugin", "shareToThirdApp", [str]);
         };
         document.addEventListener("deviceready", onDeviceReady, true);
     }
    //清除缓存
    var clearWebViewCache=function(){
         var onDeviceReady = function() {
                   cordova.exec(null, null, "MainCordovaActivityPlugin", "clearWebViewCache", [""]);
          };
          document.addEventListener("deviceready", onDeviceReady, true);
     }
     var wxAuthorizationLogin=function(callbackSuccess){
         var onDeviceReady = function() {
                   cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "wxAuthorizationLogin", [""]);
          };
          document.addEventListener("deviceready", onDeviceReady, true);
     }

    var openNearStoreList=function(){
          var onDeviceReady = function() {
                    cordova.exec(null, null, "MainCordovaActivityPlugin", "openNearStoreList", [""]);
           };
           document.addEventListener("deviceready", onDeviceReady, true);
    }

    var openCameraTokePhoto=function(callbackSuccess){
          var onDeviceReady = function() {
                    cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "openCameraTokePhoto", [""]);
           };
           document.addEventListener("deviceready", onDeviceReady, true);
     }

    var openPhotoTokePhoto=function(callbackSuccess){
          var onDeviceReady = function() {
                    cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "openPhotoTokePhoto", [""]);
           };
           document.addEventListener("deviceready", onDeviceReady, true);
     }

     //地址选择
     var addressLocation=function(callbackSuccess,json){
           var onDeviceReady = function() {
                     cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "addressLocation", [json]);
            };
            document.addEventListener("deviceready", onDeviceReady, true);
    }

    //定位当前位置
    var locationCurrentPosition=function(callbackSuccess,callbackError){
           var onDeviceReady = function() {
                  cordova.exec(callbackSuccess, callbackError, "MainCordovaActivityPlugin", "locationCurrentPosition", [""]);
            };
            document.addEventListener("deviceready", onDeviceReady, true);
    }
    //定位店铺位置
    var openStoreLocation=function(json){
       var onDeviceReady = function() {
              cordova.exec(null, null, "MainCordovaActivityPlugin", "openStoreLocation", [json]);
        };
        document.addEventListener("deviceready", onDeviceReady, true);
    }


    //
    var showTimePicker=function(callbackSuccess){
          var onDeviceReady = function() {
                    cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "showTimePicker", [""]);
           };
           document.addEventListener("deviceready", onDeviceReady, true);
     }

     //二维码条形码扫描
         var qrCodeScan=function(callbackSuccess,json){
             var onDeviceReady = function() {
                      cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "qrCodeScan", [json]);
             };
             document.addEventListener("deviceready", onDeviceReady, true);
         }
         //复制文本内容
        var copy2clipboard=function(json){
             var onDeviceReady = function() {
                      cordova.exec(null, null, "MainCordovaActivityPlugin", "copy2clipboard", [json]);
             };
             document.addEventListener("deviceready", onDeviceReady, true);
         }
        var credentialsUpload=function(callbackSuccess,json){
             var onDeviceReady = function() {
                      cordova.exec(callbackSuccess, null, "MainCordovaActivityPlugin", "credentialsUpload", [json]);
             };
             document.addEventListener("deviceready", onDeviceReady, true);
         }

     //FranchiseesPlugin插件

      //选择人流量,面积,楼层
     var franchiseesCustomerAcreageFloor=function(callbackSuccess,json){
        var onDeviceReady = function() {
                 cordova.exec(callbackSuccess, null, "FranchiseesPlugin", "franchiseesCustomerAcreageFloor", [json]);
        };
        document.addEventListener("deviceready", onDeviceReady, true);
    }

   //选择人流量,面积,楼层
    var businessLicenceTermOfValidity=function(callbackSuccess){
       var onDeviceReady = function() {
                cordova.exec(callbackSuccess, null, "FranchiseesPlugin", "businessLicenceTermOfValidity", [""]);
       };
       document.addEventListener("deviceready", onDeviceReady, true);
   }



   //productshare插件

   //选择退货原因
   var showWheelViewReasonList=function(callbackSuccess,json){
       var onDeviceReady = function() {
                cordova.exec(callbackSuccess, null, "ProductsSharePlugin", "showWheelViewReasonList", [json]);
       };
       document.addEventListener("deviceready", onDeviceReady, true);
   }

   var showInputDialogForProductPrice=function(callbackSuccess){
          var onDeviceReady = function() {
                   cordova.exec(callbackSuccess, null, "ProductsSharePlugin", "showInputDialogForProductPrice", [""]);
          };
          document.addEventListener("deviceready", onDeviceReady, true);
    }