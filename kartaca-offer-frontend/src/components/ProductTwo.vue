<template>
  <div class="product-two">
    <v-sheet
        class="pa-4 red darken-3 white--text text-center ma-4"
        tile
        min-height="120"
        @click="showProduct"
        v-show="!$store.state.productTwoState"
        rounded
    >
      <div class="headline mb-3">
        Click to view the second product on event!
      </div>
    </v-sheet>
    <v-card
        v-if="$store.state.productTwoState"
        class="my-12 mx-auto"
        max-width="374"
        min-height="625"
        elevation="4"
    >
      <v-img
          height="250"
          :src="products[1].image"
      ></v-img>

      <v-card-title>{{ products[1].name }}</v-card-title>

      <v-card-text>
        <v-row
            align="center"
            class="mx-0"
        >
          <v-rating
              :value="4.5"
              color="red"
              dense
              half-increments
              readonly
              size="14"
          ></v-rating>

          <div class="grey--text ms-4">
            4.5
          </div>
        </v-row>

        <h1 class="mt-5 mb-3 font-weight-light">
          $ {{ products[1].price }}
        </h1>

        <div>Here is our product for great sale!
          <strong>{{ products[1].name }}</strong>
          this product is waiting for a new owner. Make your offer now.
        </div>
      </v-card-text>

      <v-divider class="mx-4"></v-divider>

      <v-text-field
          class="mx-4 mt-5"
          label="Give your offer price"
          outlined
          type="number"
          rounded
          v-model="offer_price"
          color="red"
      >

      </v-text-field>

      <v-snackbar
          v-model="snackbar"
          :timeout="timeout"
          color="red"
          outlined
          bottom
          right
          rounded
      >
        {{ text }}

        <template v-slot:action="{ attrs }">
          <v-btn
              color="red"
              rounded
              dark
              x-small
              v-bind="attrs"
              @click="snackbar = false"
          >
            Close
          </v-btn>
        </template>
      </v-snackbar>


      <v-card-actions>
        <v-row>
          <v-col cols="6">
            <show-offers :offers="offers"/>
          </v-col>
          <v-col cols="6">
            <v-btn
                color="red"
                dark
                class="mx-4"
                rounded
                @click="offer"
            >
              Give Offer
            </v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import ShowOffers from "@/components/ShowOffers.vue";

export default {
  name: "ProductTwo",
  components: {ShowOffers},
  data: () => ({
    offer_price: null,
    timeout: 2000,
    text: '',
    username: '',
    snackbar: false,
    offers: [],
  }),
  props: {
    products: {
      type: Array,
      required: true
    },
    userObject: {
      type: Object,
      required: true
    }
  },
  mounted() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, frame => {
      console.log(`Connected: ${frame}`);
      this.stompClient.subscribe('/topic/messages', message => {
        this.messages.push(JSON.parse(message.body).content);
      });
    });
  },
  methods: {
    showProduct() {
      this.$store.state.productTwoState = true;
      let product = this.products[1];
      if (product === undefined) {
        this.$router.push({path: '/login'});
        this.$store.state.wrongToken = true;
        this.$store.state.productTwoState = false;
      }
      let message = {
        "product": {
          "id": product.id,
          "name": product.name,
          "text": product.text,
          "price": product.price,
          "image": product.image,
        },
        "user": {
          "id": this.userObject.id,
          "firstName": this.userObject.firstName,
          "lastName": this.userObject.lastName,
          "username": this.userObject.username
        },
        "offerPrice": 0,
      };
      this.stompClient.send('/app/ws/' + product.id, JSON.stringify(message));
      this.stompClient.subscribe('/topic/offer/' + product.id, message => {
        product.price = JSON.parse(message.body).product.price;
        this.offers.push(JSON.parse(message.body));
        this.offer_price = null;
      });
    },
    offer() {
      this.minusNumberControl();
      let product = this.products[1];
      let message = {
        "product": {
          "id": product.id,
          "name": product.name,
          "text": product.text,
          "price": product.price,
          "image": product.image,
        },
        "user": {
          "id": this.userObject.id,
          "firstName": this.userObject.firstName,
          "lastName": this.userObject.lastName,
          "username": this.userObject.username
        },
        "offerPrice": this.offer_price
      };
      this.stompClient.send('/app/ws/' + product.id, JSON.stringify(message));
      this.stompClient.subscribe('/topic/offer/' + product.id, message => {
        this.offers.push(JSON.parse(message.body));
        product.price = JSON.parse(message.body).product.price;
        this.text = JSON.parse(message.body).user.firstName + ' ' + JSON.parse(message.body).user.lastName + ' gave offer $' + JSON.parse(message.body).offerPrice + ' for ' + JSON.parse(message.body).product.name;
        this.snackbar = true
        this.offer_price = null;
      });
    },
    minusNumberControl() {
      if (this.offer_price < 0) {
        this.offer_price = null;
        this.text = 'Offer price can not be minus number.'
        this.snackbar = true
      }
    }
  },
}
</script>

<style scoped>

</style>
