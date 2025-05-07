const sharp = require('sharp');
const fs = require('fs');
const path = require('path');

const icons = [
  { input: 'schedule.svg', output: 'schedule.png' },
  { input: 'schedule-active.svg', output: 'schedule-active.png' },
  { input: 'user.svg', output: 'user.png' },
  { input: 'user-active.svg', output: 'user-active.png' }
];

const inputDir = path.join(__dirname, 'images', 'tab');
const outputDir = path.join(__dirname, 'images', 'tab');

async function convertIcons() {
  for (const icon of icons) {
    const inputPath = path.join(inputDir, icon.input);
    const outputPath = path.join(outputDir, icon.output);
    
    try {
      await sharp(inputPath)
        .resize(24, 24)
        .png()
        .toFile(outputPath);
      console.log(`Successfully converted ${icon.input} to ${icon.output}`);
    } catch (error) {
      console.error(`Error converting ${icon.input}:`, error);
    }
  }
}

convertIcons(); 