<template>
  <div>
    <h1><b-badge variant="info">Certificates</b-badge></h1>
    <b-table
      striped
      responsive="lg"
      :items="certificates"
      :fields="fields"
      :head-variant="'dark'"
    >
    </b-table>
  </div>
</template>

<script>
import api from "./backend-api";
export default {
  name: "view_certificates",
  data() {
    return {
      fields: [
        "id",
        "active",
        "commonName",
        "email",
        "country",
        "startAt",
        "endAt",
        "organisation",
        "organisationUnit",
        "extension",
        "ca",
        "issuerEmail",
      ],
      certificates: [],
    };
  },
  mounted() {
    api.getAllCertificates().then((response) => {
      this.certificates = response.data;
    });
  },
};
</script>
