lmsApp.factory("borrowerData", function(){
	var borrower = {};	
	return{
		getBorrower:function(){
			return borrower;
		},
		setBorrower:function(passedborrower){
			borrower = passedborrower;
		}	
	};	
});