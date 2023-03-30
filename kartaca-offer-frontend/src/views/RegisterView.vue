<template>
  <div class="register-container">
    <v-card class="register-card" width="640">
      <h2 class="register-title">REGISTER</h2>
      <form class="register-form">
        <div class="register-input-group">
          <v-text-field
              label="Username"
              outlined
              class="px-3 pt-4 pb-2"
              name="username"
              placeholder="Enter your username"
              v-model="username"
              prepend-icon="mdi-account"
              rounded
              dense
              color="red"
          >
          </v-text-field>
        </div>
        <div class="register-input-group">
          <v-text-field
              label="Password"
              outlined
              type="password"
              class="px-3 pt-4 pb-2"
              name="password"
              v-model="password"
              placeholder="Enter your password"
              prepend-icon="mdi-lock"
              rounded
              dense
              color="red"
          >
          </v-text-field>
        </div>
        <div class="register-input-group">
          <v-text-field
              outlined
              label="Email"
              class="px-3 pt-4 pb-2"
              name="email"
              placeholder="Enter your email"
              v-model="email"
              prepend-icon="mdi-email"
              rounded
              dense
              color="red"
          >
          </v-text-field>
        </div>
        <div class="register-input-group">
          <v-text-field
              class="px-3 pt-4 pb-2"
              label="First Name"
              name="first_name"
              placeholder="Enter your first name"
              v-model="first_name"
              prepend-icon="mdi-account"
              rounded
              outlined
              dense
              color="red"
          >
          </v-text-field>
        </div>
        <div class="register-input-group">
          <v-text-field
              class="px-3 pt-4 pb-2"
              outlined
              label="Last Name"
              name="last_name"
              placeholder="Enter your last name"
              v-model="last_name"
              prepend-icon="mdi-account"
              rounded
              dense
              color="red"
          >
          </v-text-field>
        </div>
      </form>
      <v-container class="register-actions">
        <v-btn class="register-button outlined rounded red" dark outlined rounded  @click="$router.push({path: '/login'})">Log in</v-btn>
        <v-btn class="register-button register-register" @click="register" outlined>Kayıt Ol</v-btn>
      </v-container>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "RegisterView",
  data: () => ({
    username: "",
    password: "",
    email: "",
    first_name: "",
    last_name: "",
  }),
  methods: {
    register() {
      axios.post('http://localhost:8080' + '/api/auth/register', {
        username: this.username,
        password: this.password,
        email: this.email,
        firstName: this.first_name,
        lastName: this.last_name,
      })
          .then(response => {
            if (response.status === 201) {
              this.$router.push({path: '/login'})
            }
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f2f2f2;
}

.register-card {
  width: 350px;
  padding: 30px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
}

.register-title {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
}

.register-form {
  display: flex;
  flex-direction: column;
}

.register-input-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 7px;
}


.register-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

</style>