void setup()
{
  pinMode(2,OUTPUT);
  pinMode(3,OUTPUT);
  digitalWrite(2,0);
  digitalWrite(3,0);
  pinMode(7,OUTPUT);
  pinMode(8,OUTPUT);
  digitalWrite(7,0);
  digitalWrite(8,0);
  //pinMode(A0,INPUT);
  Serial.begin(57600);
  int a=0;
    while(a==0)
    {
      a=Serial.read();
    }
}

void loop()
{
  
  int times = 1;
  for(int a = 0;a<1800;a++)
  {
    if(a%(1800/300)==0)
    Serial.println((1042+512-analogRead(A0))%256);
  StepHoriz(times);
  }
  digitalWrite(8,0);
  for(int a = 0;a<900;a++)
  StepVert(times);
  digitalWrite(3,1);
  for(int a = 0;a<1830;a++)
  {
    if(a%(1830/300)==0)
    Serial.println((1042+512-analogRead(A0))%256);
  StepHoriz(times);
  }
  
  digitalWrite(8,0);
  for(int a = 0;a<900;a++)
  StepVert(times);
  
  digitalWrite(3,0);
  
}

void StepHoriz(int delayer)
{
  digitalWrite(2,0);
  delay(delayer);
  digitalWrite(2,1);
  //delay(delayer);
}

void StepVert (int delayer)
{
  digitalWrite(7,0);
  delay(delayer);
  digitalWrite(7,1);
  //delay(delayer);
}
