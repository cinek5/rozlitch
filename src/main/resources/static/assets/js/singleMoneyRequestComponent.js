/**
 * Created by Cinek on 18.03.2018.
 */
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
    '<a v-if="moneyreq.status==MONEY_REQUEST_STATUS.REQUESTED && logged_in_user!=moneyreq.creator.username.toString() " href="#" class="button button-primary" v-on:click="setStatus(moneyreq,MONEY_REQUEST_STATUS.ACCEPTED)" type="button">Accept request</a>'+
    '<a v-if="moneyreq.status==MONEY_REQUEST_STATUS.ACCEPTED && logged_in_user==moneyreq.creator.username.toString() " href="#" class="button button-primary" v-on:click="setStatus(moneyreq,MONEY_REQUEST_STATUS.FINISHED)" type="button">Finish request</a>'+
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
