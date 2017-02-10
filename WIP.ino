void setup()
{
   pinMode(8,OUTPUT);
   pinMode(7,OUTPUT);
   pinMode(2,OUTPUT);
   pinMode(3,OUTPUT);
   digitalWrite(8,LOW);
    digitalWrite(7,LOW);
    digitalWrite(2,LOW);
    digitalWrite(3,LOW);
    pinMode(A0,INPUT);
    Serial.begin(57600);
    int a=0;
    while(a==0)
    {
      a=Serial.read();
    }
 }

void loop()
{
  digitalWrite(8,LOW);
  for (int appa = 0;appa<1000;appa++)
  {
    digitalWrite(7,HIGH);
     //delay(10);
     digitalWrite(7,LOW);
     delay(4);
     int bacap = 512-analogRead(A0);
     bacap= bacap*3;
     if(bacap>255)
       bacap = 255;
     if(bacap<0)
     bacap = 0;
     Serial.write(bacap);
  }
  digitalWrite(8,HIGH);
  //digitalWrite(3,HIGH);
  delay(1);
  for (int appa = 0;appa<2000;appa++)
  {
    digitalWrite(7,HIGH);
     //delay(10);
     digitalWrite(7,LOW);
     delay(4);
     int bacap = 512-analogRead(A0);
     bacap= bacap*3;
     if(bacap>255)
       bacap = 255;
     if(bacap<0)
     bacap = 0;
     Serial.write(bacap);
     
  }
  digitalWrite(8,HIGH);
  //digitalWrite(8,LOW);
     delay(25);
     digitalWrite(3,LOW);
   
for(int appa = 0;appa<5000;appa++)
{
  //digitalWrite(8,LOW);
 digitalWrite(2,HIGH);
     delay(2);
     digitalWrite(2,LOW);
     delay(2);
     int bacap = 512-analogRead(A0);
     bacap= bacap*3;
     if(bacap>255)
       bacap = 255;
     if(bacap<0)
     bacap = 0;
     Serial.write(bacap);
}
digitalWrite(3,HIGH);
for(int appa = 0;appa<5000;appa++)
{
 digitalWrite(2,HIGH);
     delay(2);
     digitalWrite(2,LOW);
     delay(2);
     int bacap = 512-analogRead(A0);
     bacap= bacap*3;
     if(bacap>255)
       bacap = 255;
     if(bacap<0)
     bacap = 0;
     Serial.write(bacap);
}
}
