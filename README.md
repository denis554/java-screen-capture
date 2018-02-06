# Java Screen Capture
CLI programme to take a screenshot.
Currently you can set width, height and format (PNG, JPG, GIF) of captured screenshot.
This programme is written in Java language and as a result this is a cross-platform programme.

## Usage
```
usage: java -jar ScreenshotCapture.jar [options]

Options:
  -s,--standardstream      Get screenshot in standard output stream
  -e,--extension <arg>     Set output file extension (png|jpg|gif)
  -h,--help                Show help/usage
  -o,--output <arg>        Set output file name
  -x,--width <arg>         Set output width
  -y,--height <arg>        Set output height
```

### Notes
- If you don't sets dimensions of screenshot then that saved in dimensions if your monitor.
- If you don't sets extension (with `-e` or `--extension`) and output file (with `-o` and `--output`) then set the extension to PNG by default.

### Examples
Capture a screenshot in dimensions of your monitor and save as example.jpg in JPG format:
```
java -jar ScreenshotCapture.jar -o example.jpg
```

Capture a screenshot in 300 pixels width and save as example.png in PNG format:
```
java -jar ScreenshotCapture.jar -x 300 -s > example.png
```

## Development
You can build programme easily with [ant](http://ant.apache.org/). Just install Ant and run `ant` command and JAR file is builded in `/dist` directory.
If you want to develop this programme you should add [Common CLI](http://commons.apache.org/proper/commons-cli/) library to your project. (Jar files of Common CLI located at `/libs` directory)

## Todo
- Supports non-fullscreen screenshots.
- Set quality of screenshot.