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
        errorCommunicate: '',
        error: false


    },
    methods: {
        validateSubmit: function () {
            this.errorCommunicate = '';
            this.error = false;
            this.validateUser();
            this.validateItemsLength();
            return !this.error;
        },
        setErrorAppendMessage: function (message) {
            this.errorCommunicate += message;
            this.error = true;
        },
        checkItem: function () {
            this.error = false;
            this.errorCommunicate = '';
            this.validateItemName();
            this.validateItemCategory();
            this.validateItemPrice();
            this.validateItemQuantity();
            return !this.error;
        },
        validateItemName: function () {
            if (this.itemName == '') {
                this.setErrorAppendMessage('Item name must not be at blank  <br>');

            } else if (!this.itemName.match(/[A-Z][a-z]*$/)) {
                this.setErrorAppendMessage('Item name contains only letters and starts with capital <br>')
            }
        },
        validateUser: function() {
            if (this.user == null) {
                this.setErrorAppendMessage('You have to choose the user <br>');
            }
        },
        validateItemCategory: function () {
            if (this.itemCategory == '') {
                this.setErrorAppendMessage('Item category must not be blank <br>');
            } else if (!this.itemCategory.match(/[A-Z][a-z]*$/)) {
                this.setErrorAppendMessage('Item category contains only letters and starts with capital <br>')
            }
        },
        validateItemQuantity: function () {
            if (this.itemQuantity < 1) {
                this.setErrorAppendMessage('Item quantity must be at least 1 <br>');
                this.itemQuantity = 1;
            }
        },
        validateItemsLength: function () {
            if (this.items.length == 0) {
                this.setErrorAppendMessage('You have to add at least 1 item <br>');
            }
        },
        validateItemPrice: function () {
            if (this.itemPrice < 0) {
                this.setErrorAppendMessage('Item price must be at least 0  <br>');
                this.itemPrice = 0;
            }
        },
        addItem: function () {
            if (this.checkItem()) {
                this.items.push({
                    name: this.itemName,
                    price: this.itemPrice,
                    quantity: this.itemQuantity,
                    category: this.itemCategory
                });
            }
        },
        postAddRequest: function () {
            if (getCookie('access_token') != null && this.validateSubmit()) {
                axios.post("/moneyrequests/?access_token=" + getCookie("access_token"), {
                    requestedUser: this.user,
                    items: this.items,
                    status: "REQUESTED"
                }).then(function () {
                    document.location.replace("/userPage?access_token=" + getCookie("access_token"));
                })
                    .catch(function (error) {
                        console.log('problem with post request (add money request obj)');
                        console.log(error);
                    });
            }
        }
    },
    mounted: function () {

        if (getCookie('access_token') != null) {
            axios.get("/otherusers?access_token=" + getCookie('access_token'))
                .then((function (response) {
                    this.users = response.data;
                }).bind(this)).catch(function (error) {
                console.log("cos sie zjebalo");
            });


        }
    }


});
/**
 * Created by Cinek on 23.12.2017.
 */
