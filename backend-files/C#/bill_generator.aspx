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
 namespace WebApplication4										
 {										
    public partial class _Default : System.Web.UI.Page										
    {										
        protected void Page_Load(object sender, EventArgs e)										
        {										
            string access_key = "MERCHANT_ACCESS_KEY"; 										
            string secret_key = "MERCHANT_SECRET_KEY";										
            string return_url = "http://YOUR_SERVER/response.aspx";								
            Int32 unixTimestamp = (Int32)(DateTime.UtcNow.Subtract(new DateTime(1970, 1, 1))).TotalSeconds;										
            string txn_id = unixTimestamp.ToString();										
            Random rnd = new Random();										
            int rnd1 = rnd.Next(10000, 99999);										
            txn_id = txn_id + rnd1.ToString();								
            string value = "100"; 								
            string data_string = "merchantAccessKey=" +access_key + "&transactionId=" +txn_id + "&amount=" + value;								
            byte[] key = Encoding.ASCII.GetBytes(secret_key);​ //secret key​								
            HMACSHA1 hmac = new HMACSHA1(key);								
            MemoryStream stream = new MemoryStream(Encoding.ASCII.GetBytes(data_string));​ //data to be encoded​								
            string signature = BitConverter.ToString(hmac.ComputeHash(stream))										
            .Replace("-", "") .ToLower();​										
          								
										
            String retval = "{\"merchantTxnId\"" + ":";										
            retval = retval + "\"" + txn_id + "\",";										
            retval = retval + "\"amount\":";										
            retval = retval + "{\"value\":";										
            retval = retval + "\"100\",";										
            retval = retval + "\"currency\":\"INR\"},";										
            retval = retval + "\"requestSignature\":";										
            retval = retval + "\"" + signature + "\",";										
            retval = retval + "\"merchantAccessKey\":";										
            retval=retval+"\""+access_key+"\",";										
            retval = retval + "\"returnUrl\":";										
            retval=retval+"\""+return_url+"\"}";										
            Response.Write(retval);										
        }										
    }										
 }       

