<template>
  <div>
    <h1><b-badge variant="info">Add Certificate</b-badge></h1>
    <b-form v-on:submit.prevent @submit="onSubmit" class="add-certificate-form">
      <b-form-group id="given-name-formgroup">
        <b-form-input
          type="text"
          id="givenName"
          name="givenName"
          placeholder="Given name"
          v-model="$v.certificate.givenName.$model"
          :state="validateState('givenName')"
          aria-describedby="given-name-feedback"
        />
        <b-form-invalid-feedback id="given-name-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="given-name-formgroup">
        <b-form-input
          type="text"
          placeholder="Surname"
          v-model="$v.certificate.surname.$model"
          :state="validateState('surname')"
          aria-describedby="surname-feedback"
        />
        <b-form-invalid-feedback id="surname-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="email-formgroup">
        <b-form-input
          type="email"
          placeholder="Email"
          v-model="$v.certificate.email.$model"
          :state="validateState('email')"
          aria-describedby="email-feedback"
        />
        <b-form-invalid-feedback id="email-feedback"
          >This is a required field and must be valid email
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="email-formgroup">
        <b-form-input
          type="text"
          placeholder="Country"
          v-model="$v.certificate.country.$model"
          :state="validateState('country')"
          aria-describedby="country-feedback"
        />
        <b-form-invalid-feedback id="country-feedback"
          >This is a required field and must contain at least 2 character
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="dateStart-formgroup">
        <b-form-datepicker
          type="date"
          placeholder="Start at"
          v-model="$v.certificate.startAt.$model"
          :state="validateState('startAt')"
          aria-describedby="dateStart-feedback"
        />
        <b-form-invalid-feedback id="dateStart-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="dateEnd-formgroup">
        <b-form-datepicker
          type="date"
          placeholder="End at"
          v-model="$v.certificate.endAt.$model"
          :state="validateState('endAt')"
          aria-describedby="dateEnd-feedback"
        />
        <b-form-invalid-feedback id="dateEnd-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="organisation-formgroup">
        <b-form-input
          type="text"
          placeholder="Organisation"
          v-model="$v.certificate.organisation.$model"
          :state="validateState('organisation')"
          aria-describedby="organisation-feedback"
        />
        <b-form-invalid-feedback id="organisation-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="organisation-formgroup">
        <b-form-input
          type="text"
          placeholder="Organisation unit"
          v-model="$v.certificate.organisationUnit.$model"
          :state="validateState('organisationUnit')"
          aria-describedby="organisation-unit-feedback"
        />
        <b-form-invalid-feedback id="organisation-unit-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="organisation-formgroup">
        <b-form-input
          type="text"
          placeholder="Extension"
          v-model="$v.certificate.extension.$model"
          :state="validateState('extension')"
          aria-describedby="extension-feedback"
        />
        <b-form-invalid-feedback id="extension-feedback"
          >This is a required field
        </b-form-invalid-feedback>
      </b-form-group>

      <b-form-group id="ca-formgroup">
        <b-form-checkbox v-model="certificate.ca">
          Certificate Authority
        </b-form-checkbox>
      </b-form-group>

      <b-form-group id="issuer-email-formgroup">
        <b-form-select v-model="certificate.issuerEmail">
          <b-form-select-option :value="null"
            >Issuer Email</b-form-select-option
          >

          <b-form-select-option
            v-for="issuerEmail in issuers"
            :key="issuerEmail"
            :value="issuerEmail"
            >{{ issuerEmail }}</b-form-select-option
          >
        </b-form-select>
      </b-form-group>

      <b-form-group id="active-formgroup">
        <b-form-checkbox v-model="certificate.active">
          Active
        </b-form-checkbox>
      </b-form-group>

      <b-btn variant="success" type="submit">Add certificate</b-btn>
    </b-form>
  </div>
</template>

<script>
import Axios from "axios";
import api from "./backend-api";
import moment from "moment";

import { validationMixin } from "vuelidate";
import { required, minLength, email } from "vuelidate/lib/validators";

export default {
  name: "add_certificate",
  mixins: [validationMixin],
  data() {
    return {
      certificate: {
        givenName: null,
        surname: "",
        email: "",
        country: "",
        startAt: "",
        endAt: "",
        organisation: "",
        organisationUnit: "",
        extension: "",
        ca: false,
        issuerEmail: null,
        active: true,
      },
      issuers: [],
      form: {
        givenName: null,
      },
    };
  },
  mounted() {
    api
      .getAllCertificates()
      .then(
        (response) =>
          (this.issuers = response.data.map((element) => element.email))
      );
  },
  validations: {
    certificate: {
      givenName: {
        required,
      },
      surname: {
        required,
      },
      email: {
        required,
        email,
      },
      country: {
        required,
        minLength: minLength(2),
      },
      startAt: { required },
      endAt: { required },
      organisation: { required },
      organisationUnit: { required },
      extension: { required },
    },
  },
  methods: {
    onSubmit() {
      console.log("request");
      this.$v.certificate.$touch();
      if (this.$v.certificate.$anyError) {
        return;
      }
      this.certificate.startAt = moment(this.certificate.startAt).format(
        "YYYY/MM/DD"
      );
      this.certificate.endAt = moment(this.certificate.endAt).format(
        "YYYY/MM/DD"
      );

      api
        .addCertificate(this.certificate)
        .then((response) => console.log(response.data))
        .catch((error) => console.log(error));
    },
    validateState(name) {
      const { $dirty, $error } = this.$v.certificate[name];
      return $dirty ? !$error : null;
    },
  },
};
</script>

<style scoped>
.add-certificate-form {
  width: 25%;
  margin-left: auto;
  margin-right: auto;
}
</style>
