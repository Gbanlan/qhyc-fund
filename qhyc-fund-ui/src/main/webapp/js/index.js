var app = angular.module('registerApp', []);
var _scope = null;
app.controller('registerCtrl', function($rootScope,$scope,$interval,$http) {
	inintPram($scope)
	$scope.chooseSwichUserInfoBtn = function ($event, value) {
		if(value=="telephone"){
			$scope.isShowBindTelephone = true;
			$scope.isShowWriteBasicInfo = false;
			$scope.isShowUserCard = false;
		}else if(value=="basciInfo"){
			$scope.isShowBindTelephone = false;
			$scope.isShowWriteBasicInfo = true;
			$scope.isShowUserCard = false;
		}else if(value=="userCard"){
			$scope.isShowBindTelephone = false;
			$scope.isShowWriteBasicInfo = false;
			$scope.isShowUserCard = true;
		}
	}

	$scope.telephonePageNext = function(){
		var telephoneNum = $scope.telephoneNum;
		validatemobile(telephoneNum,$scope);
		var checkedCord = $scope.checkedCord;
		if(telephoneNum==""||telephoneNum==null){
			return;
		}
		if(checkedCord==""||checkedCord==null){
			return;
		}
		var pram = {telephoneNum:telephoneNum,checkedCord:checkedCord};
		var url = "";
		$.ajax({
            url:url,
            type:'GET',
            async:true,    //或false,是否异步
            data:pram,
            timeout:10000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,textStatus){
                console.log(data)
            },
            error:function(xhr,textStatus){
                console.log('错误')
            }
        });
		$("#telephone").removeClass("chooseBaiseInfoSelect");
		$("#basciInfo").addClass("chooseBaiseInfoSelect");
		$scope.isShowBindTelephone = false;
		$scope.isShowWriteBasicInfo = true;
		
	}
	$scope.sendCheckedCord = function(){
		var phone = $scope.telephoneNum;
		validatemobile(phone,$scope);
		$scope.isTelephoneError = false;
		var countDownNum = $scope.countDown;
		
		if(countDownNum!=null&&countDownNum!=0){return;}
		var pram = {phone:phone};
		var url = "/user/getValidationCode/"+phone;
		$.ajax({
            url:url,
            type:'GET',
            async:false,    //或false,是否异步
            //data:pram,
            timeout:10000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,textStatus){
            	if(data.status=200){
            		alert("验证码已经发送到您的手机上，请注意查收。");
            	}
            },
            error:function(){
            	  alert("验证码发送失败。");
            }
        });
        $scope.countDown=60;
        $scope.countDownTimeTicket = setInterval(function (){
        	$scope.countDown = $scope.countDown-1;
        	$("#countDown").html($scope.countDown)
           	if($scope.countDown==0){
           		clearInterval($scope.countDownTimeTicket);
           		$("#countDown").html("")
           	}
 
    	}, 1000);
	}
	$scope.basciInfoPageNext = function(){
		var userName = $scope.userName;
		var userPresonalCard = $scope.userPresonalCard;
		//validateIsUserPrasonalCardNo(userPresonalCard,$scope);
		
		var userPostCard = $scope.userPostCard;

		var userCardName = $scope.userCardName;
		alert($scope.userCardName)
		var userPostCardBank = $scope.userPostCardBank;
		var userCardBandAdr = $scope.userCardBandAdr;
		if(userName==""||userName==null){
			$scope.isUserNameError = true;
		}
		if(userPresonalCard==""||userPresonalCard==null){
			$scope.isUserPresonalCardError = true;
		}
		if(userPostCard==""||userPostCard==null){
			$scope.isUserPostCardError = true;
		}
		if(userCardName==""||userCardName==null){
			$scope.isUserCardNameError = true;
		}
		if(userPostCardBank==""||userPostCardBank==null){
			$scope.isSelectPresonalCardBinkError = true;
		}
		if(userCardBandAdr==""||userCardBandAdr==null){
			$scope.isSelectPresonalCardError = true;
		}
		if(isUserNameError||isUserPresonalCardError||isUserPostCardError||isUserCardNameError)
		$("#basciInfo").removeClass("chooseBaiseInfoSelect");
		$("#userCard").addClass("chooseBaiseInfoSelect");
		$scope.isShowWriteBasicInfo = false;
		$scope.isShowUserCard = true;
	}
})

function inintPram($scope) {

	$scope.isShowBindTelephone = true;
	$scope.isShowWriteBasicInfo = false;
	$scope.isShowUserCard = false;
	$scope.telephoneNum = "";

	$scope.userName = "";
	$scope.userPostCard = "";
	$scope.userCardName = "";
	$scope.userPostCardBank = "";
	$scope.userCardBandAdr = "";

	$scope.isTelephoneError = false;
	$scope.isCheckedCordError = false;
	$scope.isUserNameError = false;
	$scope.isUserPresonalCardError = false;
	$scope.isUserPostCardError = false;
	$scope.isUserCardNameError = false;
	$scope.isSelectPresonalCardBinkError = false;
	$scope.isUserCardBandAdrError = false;



}
function validatemobile(mobile,$scope) { 
       if(mobile.length==0) 
       { 
          $scope.isTelephoneError = true;
          //document.form1.mobile.focus(); 
          return false; 
       }     
       if(mobile.length!=11) 
       { 
           $scope.isTelephoneError = true;
           //document.form1.mobile.focus(); 
           return false; 
       } 
        
       var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
       if(!myreg.test(mobile)) 
       { 
           $scope.isTelephoneError = true;
           //document.form1.mobile.focus(); 
           return false; 
       } 
} 

function validateIsUserPrasonalCardNo(card,$scope)  {  
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
   if(reg.test(card) === false)  
   {  
       $scope.isUserPresonalCardError = true;  
       return  false;  
   }  
}  