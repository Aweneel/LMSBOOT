lmsApp.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/", {
		redirectTo: "/home"
	}).when("/home", {
		templateUrl: "home.html"
	}).when("/admin", {
		templateUrl: "admin.html"
	}).when("/crud", {
		templateUrl: "crud.html"
	}).when("/author", {
		templateUrl: "author.html"
	}).when("/viewauthors", {
		templateUrl: "authors.html"
	}).when("/addauthor", {
		templateUrl: "addauthor.html"
	}).when("/book", {
		templateUrl: "book.html"
	}).when("/viewbooks", {
		templateUrl: "books.html"
	}).when("/addbook", {
		templateUrl: "addbook.html"
	}).when("/borrower", {
		templateUrl: "borrower.html"
	}).when("/borroweractions", {
		templateUrl: "borroweractions.html"
	}).when("/librarian", {
		templateUrl: "librarian.html"
	}).when("/viewborrowed", {
		templateUrl: "viewborrowed.html"
	}).when("/checkoutbook", {
		templateUrl: "checkoutbook.html"
	}).when("/returnbook", {
		templateUrl: "returnbook.html"
	})
}])