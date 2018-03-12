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



Vue.component('money-request',{
    data: function() {
        return {
            MONEY_REQUEST_STATUS: Object.freeze({
                ACCEPTED: 'ACCEPTED',
                FINISHED: 'FINISHED',
                REQUESTED: 'REQUESTED'
            }),
            logged_in_user: ''
        }
    },
    props: ['moneyreq'],
    template:
    '<div>'+
        '<h2>Creator: {{moneyreq.creator.username.toString()}} </h2>'+
        '<h2>Requested user: {{moneyreq.requestedUser.username.toString()}} </h2>'+
        '<h1>Items: </h1>'+
        '<table class="u-full-width">'+
            '<thead>'+
            '<tr>'+
                '<th>Name</th>'+
                '<th>Category</th>'+
                '<th>Price</th>'+
                '<th>Quantity</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody>'+
                '<tr  v-for="item in moneyreq.items">'+
                    '<td>{{item.name}}</td>'+
                    '<td>{{item.category}}</td>'+
                    '<td>{{item.price}}</td>'+
                    '<td>{{item.quantity}}</td>'+
                '</tr>'+
            '</tbody>'+
        '</table>'+
        '<p>Sum: {{moneyreq.value}}</p>'+
        '<p>Status: {{moneyreq.status}}</p>'+
        '<a v-if="moneyreq.status!=MONEY_REQUEST_STATUS.ACCEPTED && logged_in_user!=moneyreq.creator.username.toString() " href="#" class="u-full-width button-primary" v-on:click="setStatus(moneyreq,MONEY_REQUEST_STATUS.ACCEPTED)" type="button">Accept request</a>'+
    '</div>',
    methods: {
        setStatus: function(moneyreq,status) {
         if (getCookie('access_token')!=null) {
         axios({
         method:'put',
         url: '/moneyrequests/status/'+moneyreq.id+'?access_token='+getCookie("access_token"),
         headers: {"Content-type": 'application/json'},
         data: JSON.stringify(status)

         }).then(function() {
         location.reload();
         } )
         .catch(function(error) {
         console.log('problem with put request (set status money request method)');
         console.log(error);
         });
         }
         }
    },
    mounted() {

         fetchUsernameAndDoSth(function(responseData) {
             this.logged_in_user = responseData;

         }.bind(this))
        console.log(this.logged_in_user)
    }




});


Vue.component('money-requests',{
    data: function() {
        return {
            money_requests: [],
            sortingParam: '',
            newRequestUrl: '/newRequest?access_token='+this.getToken()
        }
    },
    props: ['url'],
    template:
    '<div>'+
            '<p>Item sort options: </p>'+
            '<select v-on:change="switchSortMethod" v-model="sortingParam" class="u-full-width">'+
            '<option value="">Default</option>'+
            '<option value="price">Price ascending</option>'+
            '</select>'+
            '<money-request class="request" v-for="moneyreq in money_requests" v-bind:moneyreq="moneyreq"></money-request>'

    +'<p class="new_request"><a v-bind:href="newRequestUrl" class="button button-primary" type="button">Add new request </a> </p>'
    +'</div>'
    ,

    methods: {
        getToken: function() {
            return getCookie('access_token');
        },
        fetchSortedData: function(sortParam) {

            fetchMoneyRequestsAndDoSth(function(response){
                this.money_requests=response;
                console.log(response);
                console.log('sorted data fetched');
            }.bind(this),this.url,sortParam);



        },
        switchSortMethod: function()  {
            this.fetchSortedData(this.sortingParam);
        },



    },
    mounted: function(){
        if (getCookie('access_token')!=null) {
            axios.get("/getusername?access_token="+getCookie('access_token'))
                .then((function(response){
                    this.logged_in_user = response.data;
                }).bind(this)).catch(function(error) {
                console.log("cos sie zjebalo");
            });
            axios.get(this.url+"?access_token="+getCookie('access_token'))
                .then((function(response){
                    this.money_requests = response.data;
                }).bind(this)).catch(function(error) {
                console.log("cos sie zjebalo");
            });

        }
    }






});




