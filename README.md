# How to use
**Warning!!! This is not complete nor functional as a full HTML Renderer nor do I ever plan on it being usuable in that fashion**  
Once you clone the git repo, you can run using the run.sh file. Run this file with the command lines args you wish and the program will run with those args.
Two files are given in the repo, test.html and test.css. So far the renderer can pretty much only render the contents of these files.
### Flags:
-h : specify html file (required)  
-c : specify css file (required as of now)

### Example command
`./run -h test.html -c test.css`
(ignore above 'run' script. I no longer use it, but you would build the jar and run it with the same args)
## What does this do
As of right now, it renders block display, padding, and background color on html elements. Any text outside of an element tag will currently either crash the program or not show up.

## Thanks to
I would love to thank Matt Brubeck and his [guide on creating a HTML Renderer](https://limpet.net/mbrubeck/2014/08/08/toy-layout-engine-1.html) in Rust as I used this heavily to help me write what I have.
You can also find the github page for his HTML Renderer [here](https://github.com/mbrubeck/robinson)

## Future additions
- Text rendering
- Make Inline nodes space correctly
- user defined and automatic styles
