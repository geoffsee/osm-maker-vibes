# OSM Maker - WebAssembly Demo

This project demonstrates a Kotlin application compiled to WebAssembly (WASM) that processes OpenStreetMap (OSM) data.

## Files

- `demo.html` - The main HTML demo file
- `build/dist/wasmJs/developmentExecutable/` - Contains the compiled WASM artifacts:
  - `osm-maker.js` - JavaScript wrapper for the WASM module
  - `db8d653255edcefb8149.wasm` - The WebAssembly binary

## How to Run the Demo

### Option 1: Using Python HTTP Server (Recommended)

1. Open a terminal in the project root directory
2. Start a local HTTP server:
   ```bash
   python3 -m http.server 8080
   ```
   Or if you have Python 2:
   ```bash
   python -m SimpleHTTPServer 8080
   ```

3. Open your web browser and navigate to:
   ```
   http://localhost:8080/demo.html
   ```

### Option 2: Using Node.js HTTP Server

1. Install a simple HTTP server globally:
   ```bash
   npm install -g http-server
   ```

2. Run the server in the project directory:
   ```bash
   http-server -p 8080
   ```

3. Open your browser to `http://localhost:8080/demo.html`

### Option 3: Using Live Server (VS Code Extension)

1. Install the "Live Server" extension in VS Code
2. Right-click on `demo.html` and select "Open with Live Server"

## What the Demo Does

The demo showcases a simplified version of OSM data processing:

1. **Location**: Processes data for Poquoson, Virginia (bounding box: 37.115,-76.396,37.139,-76.345)
2. **Functionality**: 
   - Parses geographic coordinates
   - Calculates approximate area
   - Demonstrates WASM compilation success
3. **Output**: Displays processing results in a browser-friendly interface

## Demo Features

- 🗺️ **Interactive Interface**: Clean, professional web interface
- 📍 **Geographic Processing**: Demonstrates coordinate parsing and calculations
- 🚀 **WASM Integration**: Shows successful Kotlin-to-WASM compilation
- 🖥️ **Console Output**: Captures and displays WASM output in the browser
- ⚡ **Real-time Feedback**: Status indicators and error handling

## Technical Details

- **Source**: Kotlin multiplatform project with WASM target
- **Build Tool**: Gradle with Kotlin multiplatform plugin
- **WASM Size**: ~543 KiB
- **JavaScript Wrapper**: ~57 KiB
- **Browser Compatibility**: Modern browsers with WASM support

## Troubleshooting

### CORS Issues
If you see CORS-related errors, make sure you're serving the files through an HTTP server rather than opening the HTML file directly in the browser.

### Module Loading Issues
If the WASM module fails to load:
1. Check that all files are in the correct locations
2. Ensure your browser supports WebAssembly
3. Check the browser console for detailed error messages

### No Output
If the demo runs but shows no output:
1. The main function might execute automatically during module initialization
2. Check the browser's developer console for any logged output
3. The fallback simulation should still demonstrate the expected functionality

## Building from Source

To rebuild the WASM artifacts:

```bash
./gradlew wasmJsBrowserDevelopmentExecutableDistribution
```

This will regenerate the files in `build/dist/wasmJs/developmentExecutable/`.

## Browser Support

The demo requires a modern browser with WebAssembly support:
- Chrome 57+
- Firefox 52+
- Safari 11+
- Edge 16+

## Next Steps

This demo provides a foundation for more complex WASM applications. Potential enhancements:

1. **Real OSM Data**: Integrate with Overpass API for live data
2. **3D Visualization**: Add WebGL rendering for 3D geometry
3. **Interactive Maps**: Integrate with mapping libraries like Leaflet
4. **Performance Metrics**: Add timing and memory usage statistics