<template>
  <v-app class="animate__animated animate__fadeIn">
    <v-main>
      <router-view class="animate__animated animate__fadeIn" />
    </v-main>
  </v-app>
</template>

<script>
import 'animate.css'
export default {
  name: 'App',

  data: () => ({
    //
  }),
  created() {
    let token = localStorage.getItem('token');
    console.log(token)
    if (token === null) {
      this.$router.push({path: '/login'})
    }
  }
};
</script>
