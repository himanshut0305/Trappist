$("#submit-btn").on("click",function(){
    var fname = $("#badge-name-input").val();
    var froms = $("#badge-max-input").val();
    var fto = $("#badge-min-input").val();
    var fkpi = $("#kpi-select").val();
    var fdescription = $("#description-input").val();

    var jsonobj = {
        "name":fname,
        "from" :froms,
        "to":fto,
        "kpi":fkpi,
        "description":fdescription
    }

    console.log(jsonobj);

    sendAuthorisedPostRequest({
        url:"/api/save/badge",
        data :jsonobj,
        onSuccess:function(data){
//            data = JSON.parse(data);
            console.log(data);
        },
        onError:function(error){
            console.log(error);
        }
    });
});
