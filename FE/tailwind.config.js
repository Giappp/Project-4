/** @type {import('tailwindcss').Config} */

const colors = require('tailwindcss/colors')

module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {},
    colors: {
      transparent: 'transparent',
      current: 'currentColor',
      'color1': '#5D7B6F',
      'color2': '#A4C3A2',
      'color3': '#B0D4B8',
      'color4': '#EAE7D6',
      'color5': '#D7F9FA',
      gray: colors.gray,
    }
  },
  plugins: [],
}

