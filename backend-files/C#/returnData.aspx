 using System;										
 using System.Collections;										
 using System.Collections.Generic;										
 using System.Configuration;										
 using System.Data;										
 using System.Linq;										
 using System.Web;										
 using System.Web.Security;										
 using System.Web.UI;										
 using System.Web.UI.HtmlControls;										
 using System.Web.UI.WebControls;										
 using System.Web.UI.WebControls.WebParts;										
 using System.Xml.Linq;										
 using System.Security.Cryptography;										
 using System.Text;										
 using System.IO;										
 										
 namespace response										
 {										
     public partial class response : System.Web.UI.Page										
     {										
         public string data;										
         public string FromServer;										
         protected void Page_Load(object sender, EventArgs e)										
         {										
             string k1 = Request.Form["TxId"];										
             string k2 = Request.Form["TxStatus"];										
             string k3 = Request.Form["amount"];										
             string k4 = Request.Form["pgTxnNo"];										
             string k5 = Request.Form["issuerRefNo"];										
             string k6 = Request.Form["authIdCode"];										
             string k7 = Request.Form["firstName"];										
             string k8 = Request.Form["lastName"];										
             string k9 = Request.Form["pgRespCode"];										
             string k10 = Request.Form["addressZip"];										
 										
             string secret_key = "MERCHANT_ACCEES_KEY";										
             string verification_data = k1 + k2 + k3 + k4 + k5 + k6 + k7 + k8 + k9 + k10;										
 										
             byte[] key = Encoding.ASCII.GetBytes(secret_key);​ //secret key​										
 										
             HMACSHA1 hmac = new HMACSHA1(key);										
 										
             MemoryStream stream = new MemoryStream(Encoding.ASCII.GetBytes(verification_data));​ //data to be encoded​										
 										
             String signature = BitConverter.ToString(hmac.ComputeHash(stream))										
             .Replace("-", "").ToLower();										
             string sigrecd = Request.Form["signature"];										
             String retval = "{\"TxId\"" + ":";										
             retval = retval + "\"" + k1 + "\",";										
             retval = retval + "\"TxStatus\"" + ":";										
             retval = retval + "\"" + k2 + "\",";										
             retval = retval + "\"amount\"" + ":";										
             retval = retval + "\"" + k3 + "\",";										
             retval = retval + "\"pgTxnNo\"" + ":";										
             retval = retval + "\"" + k4 + "\",";										
             retval = retval + "\"issuerRefNo\"" + ":";										
             retval = retval + "\"" + k5 + "\",";										
             retval = retval + "\"authIdCode\"" + ":";										
             retval = retval + "\"" + k6 + "\",";										
             retval = retval + "\"firstName\"" + ":";										
             retval = retval + "\"" + k7 + "\",";										
             retval = retval + "\"lastName\"" + ":";										
             retval = retval + "\"" + k8 + "\",";										
             retval = retval + "\"pgRespCode\"" + ":";										
             retval = retval + "\"" + k9 + "\",";										
             retval = retval + "\"addressZip\"" + ":";										
             retval = retval + "\"" + k10 + "\"}";										
             Response.Write(retval);										
             Response.Write("\n");										
            // Response.Write(signature+" "+Request.Form["signature"]);										
            // Response.Write ("");										
             if (signature == Request.Form["signature"])										
                 data=retval;										
             else										
                 data="{\"error\":\"Transaction Failed\",\"Reason\":\"Signature Verfication Failed\"}";										
                 ScriptManager.RegisterStartupScript(this, this.GetType(), "Hello", "postResponse(11);", true);										
        										
         }										
     }										
 }   
