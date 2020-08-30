$("#submit-btn").on("click", function() {
    console.log("Here");


    var c = $("#esalary").val();

    var jsonObj = {

        esalary: c
    };

    sendAuthorisedPostRequest({
        url: "/api/save/employeeRecords",
        data: jsonObj,
        onSuccess: function(data){
            console.log(data);
        },
        onError: function(error){
            console.log(error);
        }
    });
});