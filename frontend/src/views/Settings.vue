<template>
  <v-container>
    <v-row class="text-center" justify="center">
      <v-col cols="12" class="pa-1">
        <v-card height="100%">
          <v-card-title>Settings</v-card-title>
          <v-card-text>
            <v-row align="center">
              <v-col cols="12">
                <v-checkbox v-model="smoothGraphs" :label="$t('smooth_graphs')">
                </v-checkbox>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Settings",
  data() {
    return {
      smoothGraphs: this.$store.state.smoothGraphs,
    };
  },
  watch: {
    smoothGraphs(smooth) {
      this.$store.commit("setSmoothGraphs", smooth);
    },
  },
};
</script>
