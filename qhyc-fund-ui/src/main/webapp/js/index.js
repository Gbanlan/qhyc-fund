var app = angular.module('registerApp', []);
var _scope = null;
app.controller('registerCtrl', function($rootScope,$scope,$interval,$http) {
	inintPram($scope)
	/*$scope.chooseSwichUserInfoBtn = function ($event, value) {
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
	}*/

	$scope.telephonePageNext = function(){
		
		var telephoneNum = $scope.telephoneNum;
		var isPass = validatemobile(telephoneNum,$scope);
		
		if(isPass==false){
			$scope.isUserNameError = true;
			return;
		}
		var checkedCord = $scope.checkedCord;
		if(telephoneNum==""||telephoneNum==null){
			return;
		}
		if(checkedCord==""||checkedCord==null){
			return;
		}
		var url = "/user/checkValidaCode/"+telephoneNum+"/"+checkedCord
		$.ajax({
            url:url,
            type:'GET',
            async:false,    //或false,是否异步
            timeout:10000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,textStatus){
                $("#telephoneCircle").animate({left:'81.5%'});
				$("#telephone").addClass("chooseBaiseInfoSelect");
				$scope.isShowBindTelephone = false;
				$scope.isShowWriteBasicInfo = true;
            },
            error:function(xhr,textStatus){
                console.log('错误')
                $("#telephoneCircle").animate({left:'81.5%'});
				$("#telephone").addClass("chooseBaiseInfoSelect");
				$scope.isShowBindTelephone = false;
				$scope.isShowWriteBasicInfo = true;

            }
        });
		
		
		
	}
	$scope.sendCheckedCord = function(){

		var telephoneNum = $scope.telephoneNum;
		var isPass = validatemobile(telephoneNum,$scope);
		if(isPass==false){
			$scope.isUserNameError = true;
			return;
		}
		$scope.isTelephoneError = false;
		var countDownNum = $scope.countDown;
		if(countDownNum!=null&&countDownNum!=0){return;}
		var url = "/user/getValidaCode/"+telephoneNum;
		$.ajax({
            url:url,
            type:'GET',
            async:false,    //或false,是否异步
            timeout:10000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,textStatus){
                console.log(data)
            },
            error:function(xhr,textStatus){
                console.log('错误')
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
		var isPass = validateIsUserPrasonalCardNo(userPresonalCard,$scope);
		if(isPass==false){return;}
		var userPostCard = $scope.userPostCard;
		var userCardName = $scope.userCardName;
		var userPostCardBank = $("#userPostCardBank").val();
		var userCardBandAdr = $scope.userCardBandAdr;
		if(userName==""||userName==null){
			$scope.isUserNameError = true
		}else{
			$scope.isUserNameError = false;
		}
		if(userPresonalCard==""||userPresonalCard==null){
			$scope.isUserPresonalCardError = true;
		}else{
			$scope.isUserPresonalCardError = false;
		}
		if(userPostCard==""||userPostCard==null){
			$scope.isUserPostCardError = true;
		}else{
			$scope.isUserPostCardError = false;
		}
		if(userCardName==""||userCardName==null){
			$scope.isUserCardNameError = true;
		}else{
			$scope.isUserCardNameError = false;
		}
		if(userPostCardBank==""||userPostCardBank==null){
			$scope.isUserPostCardBankError = true;
		}else{
			$scope.isUserPostCardBankError = false;
		}
		if(userCardBandAdr==""||userCardBandAdr==null){
			$scope.isUserCardBandAdrError = true;
		}else{
			$scope.isUserCardBandAdrError = false;
		}

		if($scope.isUserNameError||$scope.isUserPresonalCardError||$scope.isUserPostCardError||$scope.isUserCardNameError||$scope.isUserCardBandAdrError){
			return;
		}
		var pram = {phone:$scope.telephoneNum,userName:userName,userPresonalCard:userPresonalCard,userPostCard:userPostCard,userCardName:userCardName,
			userPostCardBank:userPostCardBank,userCardBandAdr:userCardBandAdr};
		var pram = {fullName:userName,identityCard:userPresonalCard,bankCard:userPostCard,cardholderName:userCardName,
				openingBank:userPostCardBank,bankAddr:userCardBandAdr};
		var url = "/user/IdCardVerification/"+userName+"/"+userPresonalCard;
		console.log(url)
		$.ajax({
            url:url,
            type:'GET',
            async:false,    //或false,是否异步
            //data:pram,
            timeout:10000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,textStatus){
            	if(data.status==200){
            		var bankCardUrl = "/user/bankCardVerification/"+userName+"/"+userPostCard;
            		$.ajax({
                        url:bankCardUrl,
                        type:'GET',
                        async:false,    //或false,是否异步
                        //data:pram,
                        timeout:10000,    //超时时间
                        dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                        success:function(data,textStatus){
                        	if(data.status==200){
                        		var url = "/user/registerUser";
                        		$.ajax({
                                    url:url,
                                    type:'GET',
                                    async:false,    //或false,是否异步
                                    data:pram,
                                    timeout:10000,    //超时时间
                                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                                    success:function(data,textStatus){
                                    	if(data.status==200){
                                    		$("#basciInfoCircle").animate({left:'81.5%'});
                            				$("#basciInfo").addClass("chooseBaiseInfoSelect");
                            				$scope.isShowWriteBasicInfo = false;
                            				$scope.isShowUserCard = true;
                                    	}else{
                                    		alert("入库失败")
                                    	}
                                        
                                    },
                                    error:function(xhr,textStatus){
                                        console.log('错误')
                                    }
                                });
                        		$("#userSubmitCircle").animate({left:'81.5%'});
                				$("#userSubmit").addClass("chooseBaiseInfoSelect");
                        	}else{
								alert("银行卡验证错误")
                        	}
                            
                        },
                        error:function(xhr,textStatus){
                            console.log('错误')
                        }
                    });
            		
    				
            	}else{
            		alert("身份证验证错误")
            	}
                
            },
            error:function(xhr,textStatus){
                console.log('错误')
            }
        });
	}

	$scope.makeSuerSubmit = function(){
		var isPass = $("#checkbox").is(':checked')
		if(isPass==false){return;}
		var url = "";
		$.ajax({
            url:url,
            type:'GET',
            async:false,    //或false,是否异步
            data:pram,
            timeout:10000,    //超时时间
            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            success:function(data,textStatus){
                $("#userSubmitCircle").animate({left:'81.5%'});
				$("#userSubmit").addClass("chooseBaiseInfoSelect");
            },
            error:function(xhr,textStatus){
                console.log('错误')
            }
        });
		
	
	}

	/*$scope.lastPage = function(){
		$scope.isShowUserCard = false;
		$scope.isShowWriteBasicInfo = true;
	}*/
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
	$scope.isUserPostCardBankError = false;
	$scope.isUserCardBandAdrError = false;



}
function validatemobile(mobile,$scope) { 
       if(mobile.length==0) 
       { 
          $scope.isTelephoneError = true;
          return false; 
       }     
       if(mobile.length!=11) 
       { 
           $scope.isTelephoneError = true;
           return false; 
       } 
       return true;
} 

function validateIsUserPrasonalCardNo(card,$scope)  {  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
   if(reg.test(card) === false){  
       $scope.isUserPresonalCardError = true;  
       return  false;  
   }else{
   	return true;
   }  
}  