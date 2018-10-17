lmsApp.controller("borrowerController", function($scope, $http, lmsService, borrowerData,
		lmsConstants, $window, $location, Pagination) {
	if ($location.path() === '/borrower') {
		lmsService.initBorrower(lmsConstants.LMS_HOST + lmsConstants.INIT_BORROWER).then(function(result) {
			$scope.borrower = result;
		})
	}
	
	$scope.verifyCardNo = function(borrower){
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.VERIFY_CARD, borrower).then(function(result) {
			if(result.length == 0)
				$scope.borrower.name = "Verification failed, Try Again!";
			else if(result.length ==1){
				borrowerData.setBorrower(result[0]);
				$window.location = "#/borroweractions";
			}
		})
	}
	
})