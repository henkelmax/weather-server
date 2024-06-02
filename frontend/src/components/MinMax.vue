<template>
  <v-card height="100%">
    <v-card-text>
      <v-row align="center">
        <v-col cols="12" class="pb-0">
          <span class="text-h5 text-grey-lighten-1"> {{ title }} </span>
        </v-col>
        <v-col cols="12" class="pb-0">
          <span class="text-h4">
            {{ value }}
          </span>
        </v-col>
        <v-col cols="6">
          <v-icon small class="mr-1"> mdi-format-vertical-align-top</v-icon>
          <span class="text-grey-lighten-1"> {{ max }} </span>
        </v-col>
        <v-col cols="6">
          <v-icon small class="mr-1"> mdi-format-vertical-align-bottom</v-icon>
          <span class="text-grey-lighten-1"> {{ min }}</span>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{
  title: string,
  value: string,
  max: string
  min: string,
}>();
</script>
