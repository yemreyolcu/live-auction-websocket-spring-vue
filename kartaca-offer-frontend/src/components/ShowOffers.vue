<template>
  <div>
    <v-btn color="red" dark @click="dialog = true" class="mx-4" outlined rounded>
      Show Offers
    </v-btn>
    <v-dialog v-model="dialog" max-width="760">
      <v-card>
        <v-card-title>
          Offers List
        </v-card-title>
        <v-card-text>
          <v-data-table
              :headers="headers"
              :items="uniqueItems"
              :items-per-page="5"
              class="elevation-1"
              no-data-text="No data available"
          >

            <template v-slot:[`item.userData`]="{item}">
              <v-chip
                  text-color="white"
                  color="red"
                  v-if="item"

              >
                {{ item.user.firstName + " " + item.user.lastName }}
              </v-chip>
            </template>

            <template v-slot:[`item.offerPrice`]="{item}">
              <v-chip
                  text-color="red"
                  color="red"
                  v-if="item"
                  outlined
              >
                {{ item.offerPrice }}
              </v-chip>
            </template>

            <template v-slot:[`item.date`]="{item}">
              <v-chip
                  text-color="red"
                  v-if="item"
                  color="red"
                  outlined
              >
                {{ getDate() }}
              </v-chip>
            </template>

            <template v-slot:[`item.status`]="{item}">
              <v-chip
                  style="max-width: 85px; min-width: 85px"
                  text-color="white"
                  v-if="item"
                  :color="getStatusText(item.id) === 'Max Offer' ? 'green' : 'red'"
              >
                {{ getStatusText(item.id) }}
              </v-chip>
            </template>

          </v-data-table>


        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn text @click="dialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  name: "ShowOffers",
  props: {
    offers: {
      type: Array,
      required: true
    }
  },
  methods: {
    getStatusText(id) {
      let lastItem = this.offers[this.offers.length - 1]
      if (lastItem.id === id) {
        return "Max Offer"
      } else {
        return "Not Max"
      }
    },
    getDate() {
      return new Date().toLocaleString()
    },
  },
  computed: {
    uniqueItems() {
      return this.offers.filter((item, index, self) =>
              index === self.findIndex((t) => (
                  t.user.firstName === item.user.firstName &&
                  t.offerPrice === item.offerPrice &&
                  t.offerPrice !== 0 &&
                  t.id === item.id
              ))
      )
    }
  },
  data: () => ({
    dialog: false,
    headers: [
      {
        text: "From",
        align: "start",
        sortable: false,
        value: "userData",
        width: "30%"
      },
      {
        text: "Offer",
        value: "offerPrice",
        width: "20%"
      },
      {
        text: "Date",
        value: "date",
        sortable: false,
        width: "40%"
      },
      {
        text: "Is Max Offer?",
        value: "status",
        sortable: false,
        width: "10%"
      }
    ]
  })
}
</script>


<style scoped>

</style>