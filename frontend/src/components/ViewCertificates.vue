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
      <template v-slot:cell(issue)="row">
        <b-form-group>
          <b-form-input
            type="text"
            placeholder="Issuer email"
            v-model="row.issuerEmail"
          />
        </b-form-group>
        <b-form-group>
          <b-form-select v-model="row.certificateType">
            <b-form-select-option :value="'root'">Root</b-form-select-option>
            <b-form-select-option :value="'intermediate'"
              >Intermediate</b-form-select-option
            >
            <b-form-select-option :value="'endEntity'"
              >End entity</b-form-select-option
            >
          </b-form-select>
        </b-form-group>
        <b-button
          @click="issue(row.certificateType, row.issuerEmail, row.item.email)"
        >
          Issue
        </b-button>

        <!-- As `row.showDetails` is one-way, we call the toggleDetails function on @change -->
      </template>
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
        { key: "issue", label: "Issue", tdClass: "issue-column" },
      ],
      certificates: [],
    };
  },
  mounted() {
    api.getAllCertificates().then((response) => {
      this.certificates = response.data;
    });
  },
  methods: {
    issue(type, email_issuer, email_subject) {
      console.log(email_subject);
      if (!email_issuer) return;
      if (type == "root")
        api
          .issueRoot(email_issuer, email_subject)
          .then((response) => console.log(response));
      if (type == "intermediate")
        api
          .issueIntermediate(email_issuer, email_subject)
          .then((response) => console.log(response));
      if (type == "endEntity")
        api
          .issueEndEntity(email_issuer, email_subject)
          .then((response) => console.log(response));
    },
  },
};
</script>
<style>
.issue-column {
  min-width: 200px;
}
</style>
