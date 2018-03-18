/**
 * Created by Cinek on 18.03.2018.
 */
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
                console.log(error);
            });
            axios.get(this.url+"?access_token="+getCookie('access_token'))
                .then((function(response){
                    this.money_requests = response.data;
                }).bind(this)).catch(function(error) {
                console.log(error);
            });

        }
    }






});

