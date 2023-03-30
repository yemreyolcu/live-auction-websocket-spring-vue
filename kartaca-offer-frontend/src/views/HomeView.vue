<template>
  <div class="home-view">
    <h1 class="home-title">Our Products</h1>
    <p class="home-welcome text-right px-5 mr-16">Welcome {{ username }}</p>
    <v-row>
      <v-col cols="4" class="mx-auto">
        <product-one
            :products="products"
            :userObject="userObject"
        />
      </v-col>
      <v-col cols="4" class="mx-auto">
        <product-two
            :products="products"
            :userObject="userObject"
        />
      </v-col>
      <v-col cols="4" class="mx-auto">
        <product-three
            :products="products"
            :userObject="userObject"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import ProductThree from "@/components/ProductThree.vue";
import ProductTwo from "@/components/ProductTwo.vue";
import ProductOne from "@/components/ProductOne.vue";
import axios from "axios";


export default {
  name: 'HomeView',
  components: {
    ProductOne,
    ProductTwo,
    ProductThree
  },
  data: () => ({
    token: localStorage.getItem('token'),
    products: [],
    snackbar: false,
    timeout: 2000,
    text: ' Teklif verdi',
    username: '',
    userObject: {},
    stompClient: null,
    messages: [],
    message: '',
  }),
  mounted() {
    axios.get('http://localhost:8080' + '/api/product/products', {
      headers: {
        'Authorization': `Bearer ${this.token}`
      }
    }).then(response => {
      this.products = response.data
      axios.get('http://localhost:8080' + '/api/auth/logged-in-user', {
        headers: {
          'Authorization': `Bearer ${this.token}`
        }
      }).then(response => {
        this.userObject = response.data
        this.username = this.userObject.username
      })
    }).catch(error => {
      if (error.response.status === 401) {
        this.$router.push('/login')
      }
    })
  },
}
</script>
<style scoped>
.home-view {
  background-color: #f2f2f2;
  padding: 50px 0;
}

.home-title {
  font-size: 36px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
}

.navbar-menu ul {
  display: flex;
  list-style: none;
}

.navbar-menu li {
  margin-right: 10px;
}

.navbar-menu a {
  color: #fff;
  text-decoration: none;
  font-weight: bold;
}

.navbar-menu a:hover {
  text-decoration: underline;
}

.navbar-title h3 {
  margin: 0;
}

 .home-welcome {
   font-weight: 300;
   font-size: 20px;
   color: #ff5252;
   margin-top: 20px;
 }
</style>
