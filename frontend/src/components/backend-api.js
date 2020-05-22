import axios from "axios";

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000,
});

export default {
  hello() {
    return AXIOS.get(`/hello`);
  },
  getUser(userId) {
    return AXIOS.get(`/user/` + userId);
  },
  createUser(firstName, lastName) {
    return AXIOS.post(`/user/` + firstName + "/" + lastName);
  },
  getSecured(user, password) {
    return AXIOS.get(`/secured/`, {
      auth: {
        username: user,
        password: password,
      },
    });
  },
  addCertificate(certificate) {
    return AXIOS.post("/certificates", certificate);
  },
  getAllCertificates() {
    return AXIOS.get("/certificates");
  },
  revokeCertificate(aliasIssuer, aliasCertificate) {
    return AXIOS.get(`/certificates/revoke/${aliasIssuer}/${aliasCertificate}`);
  },
  issueRoot(email_issuer, email_subject) {
    return AXIOS.post(`/certificates/issue/${email_issuer}/${email_subject}`);
  },
  issueIntermediate(email_issuer, email_subject) {
    return AXIOS.post(
      `/certificates/issue/intermediate/${email_issuer}/${email_subject}`
    );
  },
  issueEndEntity(email_issuer, email_subject) {
    return AXIOS.post(
      `/certificates/issue/endentity/${email_issuer}/${email_subject}`
    );
  },
};
