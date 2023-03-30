<template>
  <div class="login-container">
    <v-card class="login-card" width="640">
      <h2 class="login-title">LOG IN</h2>
      <form class="login-form">
        <div class="login-input-group">
          <v-text-field
              class="px-3 pt-4 pb-2"
              name="username"
              placeholder="Enter your username"
              v-model="username"
              prepend-icon="mdi-account"
              label="Username"
              rounded
              outlined
              dense
              color="red"
          >
          </v-text-field>
        </div>
        <div class="login-input-group">
          <v-text-field
              type="password"
              class="px-3 pt-4 pb-2"
              name="password"
              v-model="password"
              placeholder="Enter your password"
              prepend-icon="mdi-lock"
              label="Password"
              rounded
              outlined
              dense
              color="red"
          >
          </v-text-field>
        </div>
        <v-container class="login-actions">
          <v-btn class="login-button login-register outlined rounded red " dark outlined rounded  @click="$router.push({path: '/register'})">Register</v-btn>
          <v-btn class="login-button login-login" style="color: red" @click="login" outlined>Log in</v-btn>
        </v-container>
      </form>
    </v-card>
  </div>

</template>

<script>
import axios from 'axios';

export default {
  name: "LoginView",
  data: () => ({
    username: "",
    password: "",
    token: "",
    products: [],
  }),
  methods: {
    login() {
      axios.post('http://localhost:8080' + '/api/auth/login', {
        username: this.username,
        password: this.password
      })
          .then(response => {
            console.log(response.data)
            localStorage.setItem('token', response.data.jwt)
            this.token = localStorage.getItem('token');
            this.$store.state.storeToken = response.data.jwt
            console.log(this.token)
            axios.get('http://localhost:8080' + '/api/product/products', {
              headers: {
                'Authorization': `Bearer ${this.token}`,
                'Content-Type': 'application/json'
              }
            })
                .then(response => {
                  this.products = response.data
                })
            this.$router.push({path: '/'})
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f2f2f2;
}

.login-card {
  width: 350px;
  padding: 30px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.login-input-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.login-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}




</style>