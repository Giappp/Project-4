/** @type {import('tailwindcss').Config} */

const colors = require('tailwindcss/colors')

module.exports = {
  content: [
    "./src/**/*.{html,ts}",
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      keyframes: {
        expandHeight: {
          '0%': { maxHeight: '0' },
          '100%': { maxHeight: '200px' }, // Set the max height here
        },
        collapseHeight: {
          '0%': { maxHeight: '200px'}, // Set the max height same as opening
          '100%': { maxHeight: '0' },
        },
      },
      animation: {
        'expand-dropdown': 'expandHeight 0.5s ease-in-out',
        'collapse-dropdown': 'collapseHeight 0.5s ease-in-out',
      },
    },
    colors: {
      transparent: 'transparent',
      current: 'currentColor',
      'color1': '#5D7B6F',
      'color2': '#A4C3A2',
      'color3': '#B0D4B8',
      'color4': '#EAE7D6',
      'color5': '#D7F9FA',
      gray: colors.gray,
      slate: colors.slate,
      green: colors.green,
      blue: colors.blue,
      white: colors.white,
      amber: colors.amber
    }
  },
  plugins: [
    require('flowbite/plugin')
  ],
}

