<template>
  <v-card height="100%">
    <v-card-text>
      <v-row align="center">
        <v-col cols="12" class="pb-0">
          <span class="text-h5"> {{ title }} </span>
        </v-col>
        <v-col cols="12" class="pb-0">
          <span class="text-h4 white--text">
            {{ value }}
          </span>
        </v-col>
        <v-col cols="6">
          <v-icon small class="mr-1"> mdi-format-vertical-align-top </v-icon>
          <span> {{ max }} </span>
        </v-col>
        <v-col cols="6">
          <v-icon small class="mr-1"> mdi-format-vertical-align-bottom </v-icon>
          <span> {{ min }}</span>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "MinMax",
  props: {
    title: {
      type: String,
    },
    value: {
      type: String,
    },
    max: {
      type: String,
    },
    min: {
      type: String,
    },
  },
};
</script>
