lmsApp.controller("borrowerActionsController", function($scope, $http, lmsService, borrowerData,
		lmsConstants, $window, $location, Pagination) {
	$scope.borrower = borrowerData.getBorrower();
	var x= document.getElementById("sep1");
	var y= document.getElementById("rightpane1");
	
	x.style.display = "none";
	y.style.display = "none";
	document.getElementById("rightpane2").style.display = "none";
	document.getElementById("listOfBooksBorrowed").style.display = "none";
	
	$scope.readAllBranchesborrowed = function(borrower){
		document.getElementById("listOfBranchesBorrowed").style.display = "block";
		document.getElementById("listOfBooksBorrowed").style.display = "none";
		document.getElementById("rightpane2").style.display = "none";
		document.getElementById("listOfBooks").style.display = "none";
		document.getElementById("listOfBranches").style.display = "none";
		
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_BORROWED_BRANCHES, borrower).then(function(result) {
			$scope.branches = result;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.branches.length/$scope.pagination.perPage);
			x.style.display = "block";
			y.style.display = "block";
			if(result.length > 0)
				document.getElementById("message1").innerHTML = "Following are the branches where you have borrowed books from. Click on any branch to view the books borrowed from that branch.";	
			else
				document.getElementById("message1").innerHTML = "You have not borrowed any book from any library branch. Check out some books using the button at your left pane!"
		})		
	}
	
	$scope.readAllBooksBranch = function(branch){
		var bookBranch = {
				branchId : branch.branchId,
				cardNo : $scope.borrower.cardNo
			}
		$scope.branch = branch;
		document.getElementById("listOfBranchesBorrowed").style.display = "none";
		document.getElementById("listOfBooksBorrowed").style.display = "block";
		document.getElementById("rightpane2").style.display = "none";
		document.getElementById("listOfBooks").style.display = "none";
		document.getElementById("listOfBranches").style.display = "none";
		document.getElementById("message1").innerHTML = "Books that you have borrowed from " + branch.branchName + "."
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_BOOKS_BRANCH, bookBranch).then(function(result) {
			$scope.books = result;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.books.length/$scope.pagination.perPage);
		})
	}
	
	$scope.returnBook = function(book){
		var bookLoan = {
				bookId : book.bookId,
				branchId : $scope.branch.branchId,
				cardNo : $scope.borrower.cardNo
		}
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.RETURN_BOOK, bookLoan).then(function() {
			$scope.books.splice($scope.index);
			alert("Book Returned");
			$scope.readAllBooksBranch($scope.branch);
		})		
	}
	
	$scope.readAllBranches = function(){
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_BRANCHES).then(function(result) {
			$scope.branches = result;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.branches.length/$scope.pagination.perPage);
			document.getElementById("rightpane2").style.display = "block";
			document.getElementById("rightpane1").style.display = "none";
			document.getElementById("listOfBranches").style.display = "block";
			document.getElementById("listOfBooks").style.display = "none";
			document.getElementById("listOfBooksBorrowed").style.display = "none";
			document.getElementById("listOfBranchesBorrowed").style.display = "none";			
		})
	}
	
	$scope.readAllBooksInBranch= function(branch){
		var bookBranch = {
				branchId : branch.branchId,
				cardNo : $scope.borrower.cardNo
			}
		$scope.branch = branch;
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_BOOKS_CHECKOUT, bookBranch).then(function(result) {
			$scope.books = result;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.books.length/$scope.pagination.perPage);
			document.getElementById("rightpane2").style.display = "block";
			document.getElementById("rightpane1").style.display = "none";
			document.getElementById("listOfBranches").style.display = "none";
			document.getElementById("listOfBooks").style.display = "block";
			document.getElementById("listOfBooksBorrowed").style.display = "none";
			document.getElementById("listOfBranchesBorrowed").style.display = "none";			
		})
	}
	
	$scope.checkOutBook = function(book){
		var bookLoan = {
				bookId: book.bookId,
				branchId : $scope.branch.branchId,
				cardNo : $scope.borrower.cardNo				
		}
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.CHECK_OUT, bookLoan).then(function(result) {
			$scope.books.splice($scope.index);
			alert("Book Checked Out! Check out more books or use options from left pane.");	
			$scope.readAllBooksInBranch($scope.branch);
		})
	}
	
})