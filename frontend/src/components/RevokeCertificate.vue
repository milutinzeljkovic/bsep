<template>
  <div>
    <h1><b-badge variant="info">Revoke certificate</b-badge></h1>
    <b-form
      v-on:submit.prevent
      @submit="onSubmit"
      class="revoke-certificate-form"
    >
      <b-form-group>
        <b-form-input
          type="text"
          placeholder="Alias Issuer"
          v-model="$v.aliasIssuer.$model"
          :state="validateState('aliasIssuer')"
          aria-describedby="alias-issuer-feedback"
        />
        <b-form-invalid-feedback id="alias-issuer-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group>
        <b-form-input
          type="text"
          placeholder="Alias Certificate"
          v-model="$v.aliasCertificate.$model"
          :state="validateState('aliasCertificate')"
          aria-describedby="alias-certificate-feedback"
        />
        <b-form-invalid-feedback id="alias-certificate-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-btn variant="danger" type="submit">Revoke Certificate</b-btn>
    </b-form>
  </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";
import api from "./backend-api";
export default {
  name: "revoke_certificate",
  mixins: [validationMixin],
  data() {
    return {
      aliasIssuer: "",
      aliasCertificate: "",
    };
  },
  validations: {
    aliasIssuer: { required },
    aliasCertificate: { required },
  },
  methods: {
    onSubmit() {
      this.$v.aliasIssuer.$touch();
      this.$v.aliasCertificate.$touch();
      if (this.$v.aliasIssuer.$anyError || this.$v.aliasCertificate.$anyError) {
        return;
      }
      api
        .revokeCertificate(this.aliasIssuer, this.aliasCertificate)
        .then((response) => console.log(response))
        .catch((error) => console.log(error));
    },
    validateState(name) {
      const { $dirty, $error } = this.$v[name];
      return $dirty ? !$error : null;
    },
  },
};
</script>

<style scoped>
.revoke-certificate-form {
  width: 25%;
  margin-left: auto;
  margin-right: auto;
}
</style>
