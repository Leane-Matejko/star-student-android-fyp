/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

interface EmailRequest{
    senderEmail : string,
    recipientEmail : string,
    code: string
}


import {setGlobalOptions} from "firebase-functions";
// import {onRequest} from "firebase-functions/https";
// import * as logger from "firebase-functions/logger";

import functions from "firebase-functions";
import axios from "axios";
import {defineJsonSecret} from "firebase-functions/params";

const someApiConfig = defineJsonSecret("SOMEAPI_CONFIG");

exports.sendEmail = functions.https.onCall(async (data: any, context: any) => {
  const {senderEmail, recipientEmail, code} = data as EmailRequest
  
  try {
    await axios.post(
      "https://api.brevo.com/v3/smtp/email",
      {
        sender: {email: senderEmail},
        to: [{ recipientEmail }],
        subject: "Authentication Code",
        htmlContent: `<h1>Your authentication code is: </h1>
			<h2>Code : ${code} </h2>`
      },
      {
        headers: {
          "api-key": someApiConfig.value().key,
          "Content-Type": "application/json",
        },
      }
    );

    return { success: true };

  } catch (error) {
    return {success: false, message: "Unable to send email. Try again later"};
  }
});

setGlobalOptions({maxInstances: 10});
