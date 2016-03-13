# mixplay
Aplicação android responsável por colocar música em vídeos do Instagram, não foi finalizado.

O código foi desenvolvido em cima do com.netcompss.ffmpeg.

#Converte MKV em MP4:
ffmpeg -y -i /sdcard/video.mkv -i /sdcard/asa.mp3 -strict experimental -vcodec mpeg4 -acodec aac -map 0:0 -map 1:0 -s 160x120 /sdcard/FINAL.mp4

#Adiciona música no MP4:
ffmpeg -y -i /sdcard/audio.mp3 -i /sdcard/videokit/out.mp4 -map 0:0 -map 1:0 -c:v copy -c:a libmp3lame -ar 44100 -aq 0 /sdcard/videokit/FINAL.mp4

#Cortar MP3:
ffmpeg -i input-file.mp3 -acodec copy -t 00:00:30 -ss 00:02:20 output-file.mp3
