var app = new Vue({
    el: '#app',
    data: {
        users: [],
        items: [],
        itemName: '',
        itemPrice: 0,
        itemQuantity: 1,
        itemCategory: '',
        user: null,
        errorCommunicate: ''


    },
    methods: {
        validateSubmit: function() {
            this.errorCommunicate = '';
            var error = false;
                if (this.user==null) {
                    error = true;
                    this.errorCommunicate += 'You have to choose the user <br>';
                }
                if (this.items.length==0) {
                    error = true;
                    this.errorCommunicate += 'You have to add some items <br>';
                }
            return !error;
        },
        checkItem: function() {
            var error = false;
            var message = '';
            if (this.itemPrice < 0) {
                message+='Item price must be at least 0  <br>';
                error = true;
                this.itemPrice=0;
            }
            if (this.itemName=='') {
                message+='Item name must not be at blank  <br>';
                error = true;

            }
            if (this.itemQuantity < 1) {
                message+='Item quantity must be at least 1 <br>';
                error = true;
                this.itemQuantity=1;
            }
            if (this.itemCategory=='') {
                message+='Item category must not be blank <br>';
                error = true;
            }
            if (this.items.length==0) {
                message+='You have to add at least 1 item <br>';
            }
            if (error) {
               this.errorCommunicate = message;
            }


            return !error;
        },
        addItem: function() {
            if (this.checkItem()) {
                this.items.push({
                    name: this.itemName,
                    price: this.itemPrice,
                    quantity: this.itemQuantity,
                    category: this.itemCategory
                });
            }
        },
        postAddRequest: function() {
            if (getCookie('access_token')!=null && this.validateSubmit()) {
                axios.post("/moneyrequests/?access_token="+getCookie("access_token"),{
                    requestedUser: this.user,
                    items: this.items,
                    status: "REQUESTED"
                }).then(function() {
                    document.location.replace("/userPage?access_token="+getCookie("access_token"));
                })
                    .catch(function(error) {
                        console.log('problem with post request (add money request obj)');
                        console.log(error);
                    });
            }
        }
    },
    mounted: function(){

        if (getCookie('access_token')!=null) {
            axios.get("/otherusers?access_token="+getCookie('access_token'))
                .then((function(response){
                    this.users = response.data;
                }).bind(this)).catch(function(error) {
                console.log("cos sie zjebalo");
            });


        }
    }


});/**
 * Created by Cinek on 23.12.2017.
 */
