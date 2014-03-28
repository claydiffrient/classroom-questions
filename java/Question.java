import java.net.DatagramPacket

class Question
{
   private String mText;
   private List<String> mAnswers;

   public Question(String pText, List<String> pAnswers, InetAddress pIpAddress)
   {
      mText = pText;
      mAnswers = pAnswers;
      mIpAddress = pIpAddress;

   }

}
