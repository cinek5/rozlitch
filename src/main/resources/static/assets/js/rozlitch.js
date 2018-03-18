/**
 * Created by Cinek on 30.11.2017.
 */

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}
function set_cookie(name, value) {
    document.cookie = name +'='+ value +'; Path=/;';

}
function delete_cookie( name ) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}
function checkIfTokenIsValid(token) {

    axios({
        method:'get',
        url:'oauth/check_token?token='+token,
        auth:{username:'my-trusted-client',password:'secret'},
        headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
    }).then(function(response){

    }).catch(function(error) {

    });
}



function fetchUsernameAndDoSth(callback) {
    if (getCookie('access_token')!=null) {
        axios.get("/getusername?access_token="+getCookie('access_token'))
            .then((function(response){
                callback(response.data);
            }).bind(this)).catch(function(error) {
            console.log("cos sie zjebalo");
        });

    }
}
function fetchMoneyRequestsAndDoSth(callback,url,sortParam) {
    if (getCookie('access_token') != null) {
         url+= "?access_token=" + getCookie('access_token');
        if (sortParam!="") {
            url+='&items_sort_by='+sortParam;
            console.log(url);
        }
        axios.get(url)
            .then((function (response) {
                callback(response.data);
            }).bind(this)).catch(function (error) {
            console.log("cos sie zjebalo");
        });
    }
}










