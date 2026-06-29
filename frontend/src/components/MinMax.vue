<template>
  <v-card height="100%" class="d-flex">
    <v-card-text class="align-self-center">
      <v-row class="align-center ga-2">
        <v-col cols="12" class="pb-0">
          <span class="text-headline-medium text-grey-lighten-1"> {{ title }} </span>
        </v-col>
        <v-col cols="6" class="d-flex justify-center">
          <v-icon class="mr-1 align-self-center">mdi-format-vertical-align-top</v-icon>
          <span class="text-headline-small align-self-center"> {{ max }} </span>
        </v-col>
        <v-col cols="6" class="d-flex justify-center">
          <v-icon class="mr-1 align-self-center">mdi-format-vertical-align-bottom</v-icon>
          <span class="text-headline-small align-self-center"> {{ min }}</span>
        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  max: string;
  min: string;
}>();
</script>
