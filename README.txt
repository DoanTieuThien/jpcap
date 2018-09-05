Bai lab nay muc dich de show cho moi nguoi thay lam sao de co the start va capture duoc packet thong qua net card su
dung jpcap voi nhung dieu kien nhu sau truoc khi bat dau

1. Yeu cau chuan bi truoc khi start
- JDK 7 hoac 8 >> 32 bit
- Su dung Jpcap.dll 32 Bit va phai de file dll nay vao 1 trong 2 cho: C -> widow -> system32 hoac thu muc local voi project
nhu trong demo nay
- Ban phai download va setup wireshark hoac win pcap: https://1.as.dl.wireshark.org/win64/Wireshark-win64-2.6.3.exe

2. Cau truc project
- Project nay co chua maven muc dich de lam cac bai sau, Toi se cho cac ban thay 1 mo hinh ket hop giua thread va spring.
- ThreadBase la ham abtrtract de tao ra khung xu ly doi voi cac thread
- JPcapRecvSignalBean Class nay chua xu ly lien quan den net card


--> moi gop y xin gui email: tieuthiendoan.cntt@gmail.com