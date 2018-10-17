lmsApp.controller("bookController", function($scope, $q, $http, lmsService,
		lmsConstants, $window, $location, Pagination) {
	if ($location.path() === '/viewbooks') {
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_BOOKS).then(function(result) {
					$scope.books = result;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math.ceil($scope.books.length/$scope.pagination.perPage);
				})
	} else {

		lmsService.initBook(lmsConstants.LMS_HOST + lmsConstants.INIT_BOOK).then(function(result) {
					$scope.book = result;
				})
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_AUTHORS).then(function(result) {
			$scope.authors = result;
			$scope.totalAuthors = $scope.authors.length;
		})
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_GENRES).then(function(result) {
			$scope.genres = result;
			$scope.totalGenres = $scope.genres.length;
		})
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_PUBLISHERS).then(function(result) {
			$scope.publishers = result;
			$scope.totalPublishers = $scope.publishers.length;
		})
	}

	$scope.deleteBook = function(bookId) {
		var delBook = {
				bookId : bookId
		}
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.SAVE_BOOK,
				delBook).then(
				function() {
					lmsService.getAll(
							lmsConstants.LMS_HOST
									+ lmsConstants.READ_ALL_BOOKS).then(
							function(data) {
								$scope.books = data;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math.ceil($scope.books.length/$scope.pagination.perPage);
							})
				})
	}

	
	$scope.removeAuthor = function(author) {
		var index = $scope.book.authors.indexOf(author);
	    $scope.book.authors.splice(index,1);
	}
	
	$scope.removeGenre = function(genre) {
		var index = $scope.book.genres.indexOf(genre);
	    $scope.book.genres.splice(index,1);
	}
	
	$scope.saveBook = function(){		
		if($scope.book.authors != null){
			lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.SAVE_NEW_AUTHOR,
					$scope.book.authors).then(
					function(){
						
					})	
			$scope.book.authors.push.apply($scope.book.authors, $scope.selectedAuthors);
		}
		else{
			$scope.book.authors = $scope.selectedAuthors;
		}
		
		if($scope.book.genres != null){
			lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.SAVE_NEW_GENRE,
					$scope.book.genres).then(
					function(){	
					})
			$scope.book.genres.push.apply($scope.book.genres, $scope.selectedGenres);
		}		
		else{
			$scope.book.genres = $scope.selectedGenres;
		}
		
		if($scope.selectedPublisher == null && $scope.book.publisher != null){
			lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.SAVE_NEW_PUBLISHER,
					$scope.book.publisher).then(
					function(){
					})
		}
		else{
			$scope.book.publisher = $scope.selectedPublisher;
		}

		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.SAVE_BOOK,
    			$scope.book).then(function() {
    		$window.location = "#/viewbooks";
    	})
	}
	
	
	
	$scope.addNewAuthor = function() {
	       var newAuthor = {};
	       $scope.book.authors = $scope.book.authors || [];
	       $scope.book.authors.push(newAuthor);
	   }
	$scope.addNewGenre = function() {
	       var newGenre = {};
	       $scope.book.genres = $scope.book.genres || [];
	       $scope.book.genres.push(newGenre);
	   }

	$scope.updateBook = function(){
		$scope.book.authors.push.apply($scope.book.authors, $scope.chosenAuthors);
		lmsService.postObj(lmsConstants.LMS_HOST + lmsConstants.UPDATE_BOOK,
				$scope.book).then(function() {
			$window.location = "#/viewbooks";
		})
	}
	
	$scope.editBookModal = function(book){
		$scope.book = book;
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_AUTHORS).then(function(result) {
			$scope.allAuthors = result;
		})
	}
	
	$scope.searchBooks = function(searchString){
		if(typeof(searchString)=="undefined")
			var sString="";
		else sString = searchString;
		lmsService.getAll(lmsConstants.LMS_HOST + lmsConstants.READ_ALL_BOOKS+sString).then(function(result) {
			$scope.books = result;
			$scope.pagination = Pagination.getNew(10);
			$scope.pagination.numPages = Math.ceil($scope.books.length/$scope.pagination.perPage);
		})
	}

})